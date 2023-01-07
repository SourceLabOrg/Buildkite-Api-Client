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

package org.sourcelab.buildkite.api.client.http;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;
import org.sourcelab.buildkite.api.client.Configuration;
import org.sourcelab.buildkite.api.client.exception.HttpRequestException;
import org.sourcelab.buildkite.api.client.request.Request;
import org.sourcelab.buildkite.api.client.request.RequestParameter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Underlying HTTP Client implementation making use of HttpComponents 5.x library.
 */
public class HttpComponentsClient implements Client {
    /**
     * User supplied API Client configuration.
     */
    private final Configuration configuration;

    /**
     * Constructor.
     * @param configuration User supplied API Client configuration.
     */
    public HttpComponentsClient(final Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Get reference to underlying HttpClient to make requests against.
     *
     * @return HttpClient instance.
     */
    private CloseableHttpClient getClient() {
        final HttpClientBuilder builder = HttpClientBuilder.create();

        // Inject Auth Header
        final List<Header> defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader("Authorization", "Bearer " + configuration.getApiToken(), true));
        builder.setDefaultHeaders(defaultHeaders);

        // Construct builder and return.
        return builder.build();
    }

    /**
     * Execute the given request and return the parsed response.
     * @param request The request to execute.
     * @return Response from the API.
     */
    @Override
    public HttpResult executeRequest(final Request request) {
        try (final CloseableHttpClient httpClient = getClient()) {
            switch (request.getMethod()) {
                case GET:
                    return executeGetRequest(request, httpClient);
                case DELETE:
                    return executeDeleteRequest(request, httpClient);
                default:
                    throw new IllegalArgumentException("Invalid HttpType: " + request.getMethod());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResult executeGetRequest(final Request request, final CloseableHttpClient httpClient) {
        try {
            // Construct URI including our request parameters.
            final String path = configuration.getApiUrl() + request.getPath();
            final URIBuilder uriBuilder = new URIBuilder(path)
                    .setCharset(StandardCharsets.UTF_8);

            // Attach request parameters
            for (final RequestParameter requestParameter : request.getRequestParameters().getParameters()) {
                for (final String value : requestParameter.getValues()) {
                    uriBuilder.setParameter(requestParameter.getName(), value);
                }
            }

            final HttpGet httpGet = new HttpGet(uriBuilder.build());
            return submitRequest(httpGet, httpClient);
        } catch (final URISyntaxException uriSyntaxException) {
            throw new HttpRequestException(uriSyntaxException.getMessage(), uriSyntaxException);
        } catch (final Exception exception) {
            throw new HttpRequestException(exception.getMessage(), exception);
        }
    }

    private HttpResult executeDeleteRequest(final Request request, final CloseableHttpClient httpClient) {
        final String path = configuration.getApiUrl() + request.getPath();
        final HttpDelete httpDelete = new HttpDelete(path);
        return submitRequest(httpDelete, httpClient);
    }

    private HttpResult submitRequest(final ClassicHttpRequest httpRequest, final CloseableHttpClient httpClient) {
        try (final CloseableHttpResponse response = httpClient.execute(httpRequest)) {
            final HttpEntity entity = response.getEntity();
            final String responseStr;
            if (entity != null) {
                responseStr = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            } else {
                responseStr = "";
            }

            // Collect response headers.
            final List<HttpHeader> allHeaders = new ArrayList<>();
            for (final Header header : response.getHeaders()) {
                allHeaders.add(new HttpHeader(header.getName(), header.getValue()));
            }

            // Build final abstracted result
            final HttpResult result = new HttpResult(
                    response.getCode(),
                    responseStr,
                    new HttpHeaders(allHeaders)
            );

            // and return it.
            return result;
        } catch (final IOException | ParseException e) {
            throw new HttpRequestException(e.getMessage(), e);
        }
    }
}
