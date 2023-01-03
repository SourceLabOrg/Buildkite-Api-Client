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

import org.sourcelab.buildkite.api.client.http.Client;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.PingRequest;
import org.sourcelab.buildkite.api.client.request.Request;

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

    public String ping() {
        return executeRequest(new PingRequest());
    }

    private <T> T executeRequest(final Request<T> request) {
        final HttpResult result = httpClient.executeRequest(request);
        return request.parseResponse(result);
    }
}
