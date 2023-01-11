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

import org.sourcelab.buildkite.api.client.exception.BuilderValidationException;
import org.sourcelab.buildkite.api.client.http.ClientFactory;
import org.sourcelab.buildkite.api.client.http.DefaultClientFactory;

/**
 * Configuration builder for {@see Configuration}.
 */
public final class ConfigurationBuilder {
    private String apiToken = null;
    private String apiUrl = "https://api.buildkite.com";
    private ClientFactory clientFactory = new DefaultClientFactory();

    /**
     * Constructor.
     */
    public ConfigurationBuilder() {
    }

    /**
     * Set the configured API Token.
     * @param apiToken value to set.
     * @return self.
     */
    public ConfigurationBuilder withApiToken(final String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    /**
     * Override the underlying http client library.
     * @param clientFactory Supply your own Client Factory implementation.
     * @return self.
     */
    public ConfigurationBuilder withClientFactory(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        return this;
    }

    /**
     * Validates that the supplied values are correct.
     * @throws BuilderValidationException if not valid or complete.
     */
    private void validate() {
        if (apiToken == null || apiToken.trim().isEmpty()) {
            throw new BuilderValidationException("The 'ApiToken' property must be configured.");
        }
        if (clientFactory == null) {
            throw new BuilderValidationException("The 'ClientFactory' property must be configured.");
        }
        if (apiUrl == null || apiUrl.trim().isEmpty()) {
            throw new BuilderValidationException("The 'ApiUrl' property must be configured.");
        }
    }

    /**
     * Create new Configuration instance from set values.
     * @return Configuration instance.
     * @throws IllegalStateException if improper values defined.
     */
    public Configuration build() {
        validate();
        return new Configuration(apiToken, apiUrl, clientFactory);
    }
}
