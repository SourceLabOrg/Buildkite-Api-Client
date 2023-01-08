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
import org.sourcelab.buildkite.api.client.request.BuildFiltersBuilder;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.Emoji;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.MetaResponse;
import org.sourcelab.buildkite.api.client.response.PingResponse;

import java.util.List;

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
        final ListBuildsResponse result = client.listBuilds(BuildFilters.newBuilder().withPageOptions(1, 2));
        logger.info("Result: {}", result);

        assertNotNull(result);
    }
}