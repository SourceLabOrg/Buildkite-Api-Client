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
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.GetBuildFilters;
import org.sourcelab.buildkite.api.client.request.HttpMethod;
import org.sourcelab.buildkite.api.client.request.PipelineFilters;
import org.sourcelab.buildkite.api.client.request.Request;
import org.sourcelab.buildkite.api.client.request.RequestParameter;
import org.sourcelab.buildkite.api.client.request.RequestParameters;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.Emoji;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.MetaResponse;
import org.sourcelab.buildkite.api.client.response.PingResponse;
import org.sourcelab.buildkite.api.client.response.Pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
     * Verifies the list emojis request and response parsing.
     */
    @Test
    void listEmojis() {
        final String orgSlugId = "mytestorg";

        // Setup mock expectations.
        setupMockResponse(
                "/v2/organizations/" + orgSlugId + "/emojis",
                HttpMethod.GET,
                200,
                "emoji.json"
        );

        // Call method under test.
        final List<Emoji> response = client.listEmojis(orgSlugId);

        // Verify response.
        assertNotNull(response);
        assertEquals(4159, response.size());

        // Spot check
        assertEquals("changesets", response.get(10).getName());
        assertEquals("https://buildkiteassets.com/emojis/img-buildkite-64/changesets.png", response.get(10).getUrl());

        assertEquals("home-assistant", response.get(100).getName());
        assertEquals("https://buildkiteassets.com/emojis/img-buildkite-64/home-assistant.png", response.get(100).getUrl());

        assertEquals("movie_camera", response.get(1000).getName());
        assertEquals("https://buildkiteassets.com/emojis/img-apple-64/1f3a5.png", response.get(1000).getUrl());
    }

    /**
     * Verifies the {@link BuildkiteClient#getMeta()} request and response parsing.
     */
    @Test
    void getMeta() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/meta",
            HttpMethod.GET,
            200,
            "meta.json"
        );

        // Call method under test.
        final MetaResponse response = client.getMeta();

        // Verify response.
        assertNotNull(response);
        assertEquals(3, response.getWebhookIps().size());

        assertEquals("100.24.182.113", response.getWebhookIps().get(0));
        assertEquals("35.172.45.249", response.getWebhookIps().get(1));
        assertEquals("54.85.125.32", response.getWebhookIps().get(2));
    }

    /**
     * Verifies the {@link BuildkiteClient#listBuilds()} request and response parsing.
     */
    @Test
    void listBuilds() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/builds",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 30)
                .withParameter("page", 1)
                .build(),
            200,
            "listBuilds.json"
        );

        // Call method under test.
        final ListBuildsResponse response = client.listBuilds();

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getBuilds().size());

        Build build = response.getBuilds().get(0);
        assertEquals("abc-id-1", build.getId());
        assertEquals(8, build.getNumber());
        assertEquals("scheduled", build.getState());
        assertNotNull(build.getAuthor());
        assertEquals("First Last", build.getAuthor().getName());
        assertEquals("first.last@email.com", build.getAuthor().getEmail());
        assertEquals("AuthorUserName", build.getAuthor().getUsername());
        assertNotNull(build.getCreator());
        assertEquals("creatorId1", build.getCreator().getId());
        assertEquals("First Last", build.getCreator().getName());
        assertEquals("first.last@email.com", build.getCreator().getEmail());
        assertNotNull(build.getPipeline());
        assertEquals("pipeline-id", build.getPipeline().getId());
        assertEquals("Run Tests", build.getPipeline().getName());
        assertNotNull(build.getPipeline().getProvider());
        assertEquals("github", build.getPipeline().getProvider().getId());

        build = response.getBuilds().get(1);
        assertEquals("01858542", build.getId());
        assertEquals(7, build.getNumber());
        assertEquals("scheduled", build.getState());
        assertNotNull(build.getAuthor());
        assertEquals("First Last", build.getAuthor().getName());
        assertEquals("first.last@email.com", build.getAuthor().getEmail());
        assertEquals("AuthorUserName", build.getAuthor().getUsername());
        assertNotNull(build.getCreator());
        assertEquals("creatorIdHere", build.getCreator().getId());
        assertEquals("First Last", build.getCreator().getName());
        assertEquals("first.last@email.com", build.getCreator().getEmail());
        assertNotNull(build.getPipeline());
        assertEquals("PipelineIdHere", build.getPipeline().getId());
        assertEquals("Run Tests", build.getPipeline().getName());
        assertNotNull(build.getPipeline().getProvider());
        assertEquals("github", build.getPipeline().getProvider().getId());
    }

    /**
     * Verifies the {@link BuildkiteClient#listBuilds()} request and response parsing.
     */
    @Test
    void listBuildsForOrg() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/myorgslug/builds",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 30)
                .withParameter("page", 1)
                .build(),
            200,
            "listBuilds.json"
        );

        // Call method under test.
        final ListBuildsResponse response = client.listBuilds(BuildFilters.newBuilder()
            .withOrganization("myorgslug")
        );

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getBuilds().size());

        Build build = response.getBuilds().get(0);
        assertEquals("abc-id-1", build.getId());

        build = response.getBuilds().get(1);
        assertEquals("01858542", build.getId());
    }

    /**
     * Verifies the {@link BuildkiteClient#listBuilds()} request and response parsing.
     */
    @Test
    void listBuildsForOrgAndPipeline() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/myorgslug/pipelines/mypipelineslug/builds",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 30)
                .withParameter("page", 1)
                .build(),
            200,
            "listBuilds.json"
        );

        // Call method under test.
        final ListBuildsResponse response = client.listBuilds(BuildFilters.newBuilder()
            .withPipeline("myorgslug", "mypipelineslug")
        );

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getBuilds().size());

        Build build = response.getBuilds().get(0);
        assertEquals("abc-id-1", build.getId());

        build = response.getBuilds().get(1);
        assertEquals("01858542", build.getId());
    }

    /**
     * Verifies the {@link BuildkiteClient#listBuilds()} request and response parsing.
     */
    @Test
    void listBuildsWithFilterCriteria() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/myorgslug/pipelines/mypipelineslug/builds",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 22)
                .withParameter("page", 2)
                .withParameter("creator", "myCreatorId")
                .withParameter("branch", "myBranch")
                .withParameter("state", "running", "failed")
                .build(),
            200,
            "listBuilds.json"
        );

        // Call method under test.
        final ListBuildsResponse response = client.listBuilds(BuildFilters.newBuilder()
            .withPipeline("myorgslug", "mypipelineslug")
            .withCreator("myCreatorId")
            .withBranch("myBranch")
            .withStateChooser().runningAnd().failed()
            .withPageOptions(2, 22)
        );

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getBuilds().size());

        Build build = response.getBuilds().get(0);
        assertEquals("abc-id-1", build.getId());

        build = response.getBuilds().get(1);
        assertEquals("01858542", build.getId());
    }

    /**
     * Verifies the {@link BuildkiteClient#getBuild} request and response parsing.
     */
    @Test
    void getBuild() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/orgIdSlug/pipelines/pipelineId/builds/22",
            HttpMethod.GET,
            200,
            "getBuild.json"
        );

        // Call method under test.
        final Optional<Build> response = client.getBuild("orgIdSlug", "pipelineId", 22);

        // Verify response.
        assertNotNull(response);
        assertTrue(response.isPresent());
        final Build build = response.get();

        // Spot check
        assertEquals("build-id", build.getId());
        assertEquals("failed", build.getState());
        assertEquals("creator-id", build.getCreator().getId());
        assertEquals("pipeline-id", build.getPipeline().getId());
        assertEquals("Run Tests", build.getPipeline().getName());
        assertEquals(4, build.getJobs().size());
        assertEquals("Compile & Verify", build.getJobs().get(0).getName());
        assertEquals("Run tests", build.getJobs().get(1).getName());
        assertEquals(null, build.getJobs().get(2).getName());
        assertEquals("Annotate", build.getJobs().get(3).getName());
    }

    /**
     * Verifies the {@link BuildkiteClient#getBuild} request and response parsing.
     */
    @Test
    void getBuild_withFilterCriteria() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/orgIdSlug/pipelines/pipelineId/builds/44",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("include_retried_jobs", "true")
                .build(),
            200,
            "getBuild.json"
        );

        // Call method under test.
        final Optional<Build> response = client.getBuild(GetBuildFilters.newBuilder()
            .withOrgIdSlug("orgIdSlug")
            .withPipelineIdSlug("pipelineId")
            .withBuildNumber(44)
            .withIncludeRetriedJobs(true)
        );

        // Verify response.
        assertNotNull(response);
        assertTrue(response.isPresent());
        final Build build = response.get();

        // Spot check
        assertEquals("build-id", build.getId());
        assertEquals("failed", build.getState());
        assertEquals("creator-id", build.getCreator().getId());
        assertEquals("pipeline-id", build.getPipeline().getId());
        assertEquals("Run Tests", build.getPipeline().getName());
        assertEquals(4, build.getJobs().size());
        assertEquals("Compile & Verify", build.getJobs().get(0).getName());
        assertEquals("Run tests", build.getJobs().get(1).getName());
        assertNull(build.getJobs().get(2).getName());
        assertEquals("Annotate", build.getJobs().get(3).getName());
    }

    /**
     * Verifies the {@link BuildkiteClient#listPipelines} ()} request and response parsing.
     */
    @Test
    void listPipelines() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/orgIdSlug/pipelines",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 30)
                .withParameter("page", 1)
                .build(),
            200,
            "listPipelines.json"
        );

        // Call method under test.
        final ListPipelinesResponse response = client.listPipelines("orgIdSlug");

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getPipelines().size());

        Pipeline pipeline = response.getPipelines().get(0);
        assertEquals("pipeline-id-1", pipeline.getId());
        assertEquals("pipeline-name", pipeline.getName());
        assertNotNull(pipeline.getProvider());
        assertEquals("github", pipeline.getProvider().getId());

        pipeline = response.getPipelines().get(1);
        assertEquals("pipeline-id-2", pipeline.getId());
        assertEquals("pipeline-name-2", pipeline.getName());
        assertNotNull(pipeline.getProvider());
        assertEquals("github", pipeline.getProvider().getId());
    }

    /**
     * Verifies the {@link BuildkiteClient#listPipelines} ()} request and response parsing.
     */
    @Test
    void listPipelines_withFilterCriteria() {
        // Setup mock expectations.
        setupMockResponse(
            "/v2/organizations/orgIdSlug/pipelines",
            HttpMethod.GET,
            RequestParameters.newBuilder()
                .withParameter("per_page", 22)
                .withParameter("page", 2)
                .build(),
            200,
            "listPipelines.json"
        );

        // Call method under test.
        final ListPipelinesResponse response = client.listPipelines(PipelineFilters.newBuilder()
            .withOrganization("orgIdSlug")
                .withPage(2)
                .withPerPage(22)
        );

        // Verify response.
        assertNotNull(response);

        // Spot check
        assertEquals(2, response.getPipelines().size());

        Pipeline pipeline = response.getPipelines().get(0);
        assertEquals("pipeline-id-1", pipeline.getId());
        assertEquals("pipeline-name", pipeline.getName());
        assertNotNull(pipeline.getProvider());
        assertEquals("github", pipeline.getProvider().getId());

        pipeline = response.getPipelines().get(1);
        assertEquals("pipeline-id-2", pipeline.getId());
        assertEquals("pipeline-name-2", pipeline.getName());
        assertNotNull(pipeline.getProvider());
        assertEquals("github", pipeline.getProvider().getId());
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param responseHttpCode What http code to respond with.
     * @param responseFile What mock response file to load response for.
     */
    private void setupMockResponse(final String requestedPath, final HttpMethod requestedMethod, final int responseHttpCode, final String responseFile) {
        final String responseStr = readFile(responseFile);
        setupMockResponseStr(requestedPath, requestedMethod, responseHttpCode, responseStr);
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param responseHttpCode What http code to respond with.
     * @param responseFile What mock response file to load response for.
     */
    private void setupMockResponse(final String requestedPath, final HttpMethod requestedMethod, final RequestParameters expectedRequestParameters, final int responseHttpCode, final String responseFile) {
        final String responseStr = readFile(responseFile);
        setupMockResponseStr(requestedPath, requestedMethod, expectedRequestParameters, responseHttpCode, responseStr);
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param responseHttpCode What http code to respond with.
     * @param responseStr The response string to return.
     */
    private void setupMockResponseStr(final String requestedPath, final HttpMethod requestedMethod, final int responseHttpCode, final String responseStr) {
        setupMockResponseStr(
            requestedPath,
            requestedMethod,
            new RequestParameters(new ArrayList<>()),
            responseHttpCode,
            responseStr
        );
    }

    /**
     * For setting up a mocked response.
     *
     * @param requestedPath The requested Path.
     * @param requestedMethod The requested method.
     * @param responseHttpCode What http code to respond with.
     * @param responseStr The response string to return.
     */
    private void setupMockResponseStr(final String requestedPath, final HttpMethod requestedMethod, final RequestParameters expectedRequestParameters, final int responseHttpCode, final String responseStr) {
        // Setup mock client to return http code 403
        when(mockHttpClient.executeRequest(argThat(requestArg -> {
            assertEquals(requestedPath, requestArg.getPath(), "Unexpected request path.");
            assertEquals(requestedMethod, requestArg.getMethod(), "Unexpected request method.");

            if (expectedRequestParameters.size() != requestArg.getRequestParameters().size()) {
                // Print out difference.
                logger.info("Expected Request Parameters: {}", expectedRequestParameters);
                logger.info("Found Request Parameters: {}", requestArg.getRequestParameters());
            }
            assertEquals(expectedRequestParameters.size(), requestArg.getRequestParameters().size(), "Unexpected number of request parameters");

            // Validate request parameters names and values.
            final RequestParameters foundParameters = requestArg.getRequestParameters();
            for (final String expectedParameterName : expectedRequestParameters.getParameterNames()) {
                final RequestParameter found = foundParameters.getParameterByName(expectedParameterName);
                final RequestParameter expected = expectedRequestParameters.getParameterByName(expectedParameterName);

                assertNotNull(found, "Missing parameter " + expectedParameterName);
                assertEquals(expected.getValues().size(), found.getValues().size());

                final Iterator<String> expectedItr = expected.getValues().iterator();
                final Iterator<String> foundItr = found.getValues().iterator();
                while (expectedItr.hasNext()) {
                    assertEquals(expectedItr.next(), foundItr.next());
                }
            }

            return true;
        })))
        .thenReturn(new HttpResult(responseHttpCode, responseStr));
    }

    /**
     * Utility method to help load mock responses from resources.
     * @param fileName file name to load from resources
     * @return The contents of the file, as a UTF-8 string.
     * @throws RuntimeException on error reading from resource file.
     */
    protected String readFile(final String fileName) {
        return MockResponseReader.readFile(fileName);
    }
}
