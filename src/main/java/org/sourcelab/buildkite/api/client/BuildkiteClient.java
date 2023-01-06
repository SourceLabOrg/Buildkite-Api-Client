/**
 * Copyright 2023 SourceLab.org https://github.com/SourceLabOrg/Buildkite-Api-Client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.buildkite.api.client;

import org.sourcelab.buildkite.api.client.exception.BuildkiteException;
import org.sourcelab.buildkite.api.client.exception.InvalidAccessTokenException;
import org.sourcelab.buildkite.api.client.exception.InvalidAllowedIpAddressException;
import org.sourcelab.buildkite.api.client.exception.NotFoundException;
import org.sourcelab.buildkite.api.client.http.Client;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.BuildFiltersBuilder;
import org.sourcelab.buildkite.api.client.request.DeleteAccessTokenRequest;
import org.sourcelab.buildkite.api.client.request.GetAccessTokenRequest;
import org.sourcelab.buildkite.api.client.request.GetUserRequest;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.PingRequest;
import org.sourcelab.buildkite.api.client.request.Request;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.ErrorResponse;
import org.sourcelab.buildkite.api.client.response.PingResponse;
import org.sourcelab.buildkite.api.client.response.parser.ErrorResponseParser;

import java.io.IOException;

/**
 * API Client for Buildkite's REST Api.
 */
public class BuildkiteClient {
    private final Configuration configuration;
    private final Client httpClient;

    /**
     * Constructor.
     * @param configuration The configuration for the client.
     */
    public BuildkiteClient(final Configuration configuration) {
        this.configuration = configuration;
        this.httpClient = configuration.getClientFactory().createClient(configuration);
    }

    /**
     * Make a 'test' or 'hello world' request to the Buildkite API.  Can be used to validate
     * connectivity to the API.
     * @see <a href="https://buildkite.com/docs/apis/rest-api">https://buildkite.com/docs/apis/rest-api</a>
     *
     * @return Response details from the ping request.
     * @throws BuildkiteException if API returns an error response.
     */
    public PingResponse ping() throws BuildkiteException {
        return executeRequest(new PingRequest());
    }

    /**
     * Retrieve details about the current AccessToken.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/access-token">https://buildkite.com/docs/apis/rest-api/access-token</a>
     *
     * @return Details about the current AccessToken.
     * @throws BuildkiteException if API returns an error response.
     */
    public AccessTokenResponse getAccessToken() throws BuildkiteException {
        return executeRequest(new GetAccessTokenRequest());
    }

    /**
     * Deletes/Revokes the current AccessToken.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/access-token#revoke-the-current-token">https://buildkite.com/docs/apis/rest-api/access-token#revoke-the-current-token</a>
     *
     * @return Details about the current AccessToken.
     * @throws BuildkiteException if API returns an error response.
     */
    public boolean deleteAccessToken() throws BuildkiteException {
        return executeRequest(new DeleteAccessTokenRequest());
    }

    /**
     * Retrieves the current user associated with the current access token.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/user#get-the-current-user">https://buildkite.com/docs/apis/rest-api/user#get-the-current-user</a>
     *
     * @return Details about the current User.
     * @throws BuildkiteException if API returns an error response.
     */
    public CurrentUserResponse getUser() throws BuildkiteException {
        return executeRequest(new GetUserRequest());
    }

    public String listBuilds() {
        return listBuilds(BuildFilters.newBuilder().build());
    }

    public String listBuilds(final BuildFiltersBuilder filtersBuilder) {
        return listBuilds(filtersBuilder.build());
    }

    public String listBuilds(final BuildFilters filters) {
        return executeRequest(new ListBuildsRequest(filters));
    }

    /**
     * Execute the given request, returning the parsed response, or throwing the appropriate
     * exception if an error was returned from the API.
     *
     * @param <T> The parsed response object.
     * @param request The request to execute.
     * @return The parsed response object.
     * @throws BuildkiteException if API returns an error response.
     */
    private <T> T executeRequest(final Request<T> request) throws BuildkiteException {
        final HttpResult result = httpClient.executeRequest(request);

        // Handle Errors based on HttpCode.
        if (result.getStatus() != 200 && result.getStatus() != 204) {
            handleError(result);
        }

        // Normal
        return request.parseResponse(result);
    }

    /**
     * Handle error responses from the API by throwing the appropriate exception.
     * @param errorResult Error response from REST API.
     * @throws BuildkiteException relating to specific underlying API error.
     */
    private void handleError(final HttpResult errorResult) throws BuildkiteException {
        // Attempt to parse error respone.
        String errorMessage = null;
        try {
            final ErrorResponse errorResponse = new ErrorResponseParser().parseResponse(errorResult);
            errorMessage = errorResponse.getMessage();
        } catch (final IOException e) {
            errorMessage = errorResult.getContent();
        }
        if (errorMessage != null && errorMessage.trim().isEmpty()) {
            errorMessage = null;
        }

        switch (errorResult.getStatus()) {
            case 401:
                throw new InvalidAccessTokenException(
                    errorMessage == null ? "Invalid Access Token" : errorMessage
                );
            case 403:
                throw new InvalidAllowedIpAddressException(
                    errorMessage == null
                        ?
                            "API requested from an IP address not specifically allowed by your AccessToken. "
                            + "Check the 'Allowed IP Addresses' field on your Access Token"
                        : errorMessage
                );
            case 404:
                throw new NotFoundException(
                    errorMessage == null
                            ?
                            "The URL or Resource Request could not be found"
                            : errorMessage
                );
            default:
                throw new BuildkiteException(
                    errorMessage == null ? "Unknown/Unhandled Error HttpCode: " + errorResult.getStatus() : errorMessage
                );
        }
    }
}
