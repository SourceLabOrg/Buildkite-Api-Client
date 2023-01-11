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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.CreateBuildOptions;
import org.sourcelab.buildkite.api.client.request.CreateBuildOptionsBuilder;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.PipelineFilters;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.Emoji;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.ListOrganizationsResponse;
import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.MetaResponse;
import org.sourcelab.buildkite.api.client.response.Organization;
import org.sourcelab.buildkite.api.client.response.PingResponse;
import org.sourcelab.buildkite.api.client.response.Pipeline;
import org.sourcelab.buildkite.api.client.util.BuildkiteClientUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration Test.
 *
 * Uses "real" AccessToken to make real requests and provide
 * limited assertions on the responses.  Not intended to be used as validation, but more a means
 * to play with the library and manually validate things are working as expected.
 *
 * See other tests for validation of the code performing as expected.
 */
@Tag("IntegrationTest")
class BuildkiteClientIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(BuildkiteClientIntegrationTest.class);

    private Configuration configuration;
    private BuildkiteClient client;

    private final String orgIdSlug = "sourcelaborg";
    private final String userId = "0185763d-3c4e-4489-9ac5-0634fc300173";
    private final String pipelineIdSlug = "run-tests";

    @BeforeEach
    void setUp() {
        // Load token from environment.
        final String token = new TestTokenLoader().getTestToken();

        configuration = Configuration.newBuilder()
            .withApiToken(token)
            .build();

        client = new BuildkiteClient(configuration);
    }

    /**
     * Sanity test the 'ping' request.
     */
    @Test
    void ping() {
        final PingResponse result = client.ping();
        logger.info("Result: {}", result);

        assertNotNull(result.getMessage());
        assertFalse(result.getMessage().isEmpty());
        assertNotEquals(0, result.getTimestamp());
    }

    /**
     * Sanity test the 'getAccessToken' request.
     */
    @Test
    void getAccessToken() {
        final AccessTokenResponse result = client.getAccessToken();
        logger.info("Result: {}", result);

        assertNotNull(result.getUuid());
        assertFalse(result.getScopes().isEmpty());
    }

    /**
     * Sanity test the 'deleteAccessToken' request.
     */
    @Test
    @Disabled
    void deleteAccessToken() {
        final boolean result = client.deleteAccessToken();
        logger.info("Result: {}", result);
        assertTrue(result);
    }

    /**
     * Sanity test the 'getUser' request.
     */
    @Test
    void getUser() {
        final CurrentUserResponse result = client.getUser();
        logger.info("Result: {}", result);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getEmail());
    }

    /**
     * Sanity test the 'listEmojis' request.
     */
    @Test
    void listEmojis() {
        final String orgIdSlug = "sourcelaborg";
        final List<Emoji> result = client.listEmojis(orgIdSlug);
        logger.info("Result: {}", result);

        assertNotNull(result);
    }

    /**
     * Sanity test the 'getMeta' request.
     */
    @Test
    void getMeta() {
        final MetaResponse result = client.getMeta();
        logger.info("Result: {}", result);

        assertNotNull(result);
        assertNotNull(result.getWebhookIps());
        assertFalse(result.getWebhookIps().isEmpty());
    }

    /**
     * Sanity test the 'listBuilds' request.
     */
    @Test
    void listBuilds() {
        final BuildFilters filters = BuildFilters.newBuilder()
                .withPageOptions(1, 2)
                .withStateChooser().failed()
                .build();
        final ListBuildsResponse result = client.listBuilds(filters);
        logger.info("Result: {}", result);

        assertNotNull(result, "Result should not be null.");
        assertNotNull(result.getPagingLinks(), "Paging Links should not be null.");
        assertTrue(result.getPagingLinks().hasNextUrl(), "Should have next page.");
        assertNotNull(result.getPagingLinks().getNextUrl());

        // Attempt to get next page.
        final ListBuildsResponse resultPage2 = client.nextPage(result);
        logger.info("Result: {}", result);

        assertNotNull(resultPage2, "Page 2 result should be not null.");
    }

    /**
     * Sanity test BuildkiteClientUtils.retrieveAll().
     */
    @Test
    void retrieveAll_builds() {
        final BuildFilters filters = BuildFilters.newBuilder()
                //.withStateChooser().passed()
                .withCreator(userId)
                .build();

        // Get all builds.
        final List<Build> builds = BuildkiteClientUtils.retrieveAll(filters, ListBuildsRequest.class, Build.class, client);
        logger.info("Found: {}", builds);
    }

    /**
     * Sanity test BuildkiteClientUtils.retrieveAll().
     */
    @Test
    void retrieveAll_pipelines() {
        final PipelineFilters filters = PipelineFilters.newBuilder()
                .withOrganization(orgIdSlug)
                .build();

        // Get all builds.
        final List<Pipeline> pipelines = BuildkiteClientUtils.retrieveAll(
            filters,
            ListPipelinesResponse.class,
            Pipeline.class,
            client
        );
        logger.info("Found: {}", pipelines);
    }

    /**
     * Sanity test BuildkiteClientUtils.retrieveNewestBuilds().
     */
    @Test
    void listOrganizations() {
        final ListOrganizationsResponse organizations = client.listOrganizations();
        logger.info("Found: {}", organizations);
    }

    /**
     * Sanity test BuildkiteClientUtils.retrieveNewestBuilds().
     */
    @Test
    void getOrganization() {
        final Optional<Organization> organization = client.getOrganization(orgIdSlug);
        logger.info("Found: {}", organization.get());
    }

    /**
     * Sanity test the 'listPiplines' request.
     */
    @Test
    void listPipelines() {
        final PipelineFilters filters = PipelineFilters.newBuilder()
                .withPageOptions(1, 2)
                .withOrganization(orgIdSlug)
                .build();
        final ListPipelinesResponse result = client.listPipelines(filters);
        logger.info("Result: {}", result);

        assertNotNull(result, "Result should not be null.");
        assertNotNull(result.getPagingLinks(), "Paging Links should not be null.");
    }

    @Test
    void rebuildBuild() {
        final Build build = client.rebuildBuild(orgIdSlug, pipelineIdSlug, 10);
        logger.info("Build: {}", build);
    }

    @Test
    void createBuild() {
        final CreateBuildOptionsBuilder builder = CreateBuildOptions.newBuilder()
                .withAuthor("First Last", "first.last@email.com")
                .withBranch("main")
                .withCommit("head")
                .withMessage("Test new build")
                .withOrganization(orgIdSlug)
                .withPipeline(pipelineIdSlug)
                .withMeta("meta-key", "meta-value")
                .withEnv("env_key", "env_value");

        final Build build = client.createBuild(builder);
        logger.info("Build: {}", build);
    }
}