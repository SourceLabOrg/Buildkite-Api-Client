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

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.buildkite.api.client.exception.InvalidAccessTokenException;
import org.sourcelab.buildkite.api.client.exception.InvalidAllowedIpAddressException;
import org.sourcelab.buildkite.api.client.exception.NotFoundException;
import org.sourcelab.buildkite.api.client.http.Client;
import org.sourcelab.buildkite.api.client.http.ClientFactory;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.HttpMethod;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.PingResponse;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuildkiteClientTest {

    private static Logger logger = LoggerFactory.getLogger(BuildkiteClientTest.class);

    private final String mockAccessToken = "Mock-Access-Token";
    private Configuration configuration;
    private BuildkiteClient client;
    private Client mockHttpClient;

    @BeforeEach
    void setUp() {

        mockHttpClient = mock(Client.class);

        // Inject our mock underlying http client.
        configuration = Configuration.newBuilder()
                .withApiToken(mockAccessToken)
                .withClientFactory(new ClientFactory() {
                    @Override
                    public Client createClient(final Configuration configuration) {
                        return mockHttpClient;
                    }
                })
                .build();

        client = new BuildkiteClient(configuration);
    }

    /**
     * Verifies if the API returns http code 401, we throw the appropriate invalid access token
     * exception.
     */
    @Test
    void verify401Response_shouldThrowInvalidAccessTokenException() {
        // Setup mock client to return http code 401
        when(mockHttpClient.executeRequest(any()))
            .thenReturn(new HttpResult(401, "{\"message\": \"invalid token exception message\"}"));

        // Make request
        final InvalidAccessTokenException thrownException =
            assertThrows(InvalidAccessTokenException.class, () -> client.getAccessToken());

        // Verify error message was populated.
        assertEquals("invalid token exception message", thrownException.getMessage());
    }

    /**
     * Verifies if the API returns http code 403, we throw the appropriate invalid ip address
     * exception.
     */
    @Test
    void verify403Response_shouldThrowInvalidAllowedIpAddressException() {
        // Setup mock client to return http code 403
        when(mockHttpClient.executeRequest(any()))
                .thenReturn(new HttpResult(403, ""));

        // Make request
        final InvalidAllowedIpAddressException thrownException =
                assertThrows(InvalidAllowedIpAddressException.class, () -> client.getAccessToken());

        // Verify error message was populated.
        assertTrue(thrownException.getMessage().contains("API requested from an IP address not specifically allowed by your AccessToken"));
    }

    /**
     * Verifies if the API returns http code 404, we throw the appropriate not found exception
     */
    @Test
    void verify404Response_shouldThrowInvalidAllowedIpAddressException() {
        // Setup mock client to return http code 403
        when(mockHttpClient.executeRequest(any()))
            .thenReturn(new HttpResult(404, "{\"message\": \"Not Found\"}"));

        // Make request
        final NotFoundException thrownException =
                assertThrows(NotFoundException.class, () -> client.getAccessToken());

        // Verify error message was populated.
        assertTrue(thrownException.getMessage().contains("Not Found"));
    }

    /**
     * Verifies the ping request and response parsing.
     */
    @Test
    void ping() {
        // Setup mock expectations.
        setupMockResponse(
            "/",
            HttpMethod.GET,
            200,
            "ping.json"
        );

        // Call method under test.
        final PingResponse response = client.ping();

        // Verify response.
        assertNotNull(response);
        assertEquals("\uD83D\uDEE0", response.getMessage());
        assertEquals(1672974170, response.getTimestamp());
    }

    /**
     * Verifies the AccessToken request and response parsing.
     */
    @Test
    void getAccessToken() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/access-token",
            HttpMethod.GET,
            200,
            "accessToken.json"
        );

        // Call method under test.
        final AccessTokenResponse response = client.getAccessToken();

        // Verify response.
        assertNotNull(response);
        assertEquals("uuid-value-here", response.getUuid());
        assertFalse(response.getScopes().isEmpty());
        assertEquals(3, response.getScopes().size());
        assertTrue(response.getScopes().contains("read_agents"));
        assertTrue(response.getScopes().contains("write_agents"));
        assertTrue(response.getScopes().contains("read_teams"));
    }

    /**
     * Verifies the delete AccessToken request and response parsing.
     */
    @Test
    void deleteAccessToken() {
        // Setup mock expectations.
        setupMockResponseStr(
            "/v2/access-token",
            HttpMethod.DELETE,
            204,
            ""
        );

        // Call method under test.
        final boolean response = client.deleteAccessToken();

        // Verify response.
        assertTrue(response);
    }

    /**
     * Verifies the get current user request and response parsing.
     */
    @Test
    void getUser() {
        // Setup mock expectations.
        setupMockResponse(
                "/v2/user",
                HttpMethod.GET,
                200,
                "user.json"
        );

        // Call method under test.
        final CurrentUserResponse response = client.getUser();

        // Verify response.
        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("first.last@email.com", response.getEmail());
        assertEquals("First Last", response.getName());
        assertEquals("https://www.gravatar.com/avatar/ABC", response.getAvatarUrl());
        assertEquals("abc==", response.getGraphqlId());
        assertEquals("2023-01-03T06:06:42.765Z[UTC]", response.getCreatedAt().toString());
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param httpCode What http code to respond with.
     * @param responseFile What mock response file to load response for.
     */
    private void setupMockResponse(final String requestedPath, final HttpMethod requestedMethod, final int httpCode, final String responseFile) {
        final String responseStr = readFile(responseFile);
        setupMockResponseStr(requestedPath, requestedMethod, httpCode, responseStr);
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param httpCode What http code to respond with.
     * @param responseStr The response string to return.
     */
    private void setupMockResponseStr(final String requestedPath, final HttpMethod requestedMethod, final int httpCode, final String responseStr) {
        // Setup mock client to return http code 403
        when(mockHttpClient.executeRequest(argThat(requestArg -> {
            assertEquals(requestedPath, requestArg.getPath());
            assertEquals(requestedMethod, requestArg.getMethod());
            return true;
        })))
        .thenReturn(new HttpResult(httpCode, responseStr));
    }

    /**
     * Utility method to help load mock responses from resources.
     * @param fileName file name to load from resources
     * @return The contents of the file, as a UTF-8 string.
     * @throws RuntimeException on error reading from resource file.
     */
    protected String readFile(final String fileName) {
        final URL inputFile = getClass().getClassLoader().getResource("mockResponses/" + fileName);
        try {
            return IOUtils.toString(inputFile, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
