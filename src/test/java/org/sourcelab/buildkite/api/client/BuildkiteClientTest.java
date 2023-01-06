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
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.PingResponse;

import static org.junit.jupiter.api.Assertions.*;

class BuildkiteClientTest {

    private static Logger logger = LoggerFactory.getLogger(BuildkiteClientTest.class);

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
     * Sanity test the 'ping' request.
     */
    @Test
    void accessToken() {
        final AccessTokenResponse result = client.accessToken();
        logger.info("Result: {}", result);

        assertNotNull(result.getUuid());
        assertFalse(result.getScopes().isEmpty());
    }
}