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

/**
 * Configuration builder for {@see Configuration}.
 */
public final class ConfigurationBuilder {
    private String apiToken = null;

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
    public ConfigurationBuilder withApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    /**
     * Validates that the supplied values are correct.
     * @throws IllegalStateException if improper values defined.
     */
    private void validate() {
        if (apiToken == null) {
            throw new IllegalStateException("The 'ApiToken' property must be configured.");
        }
    }

    /**
     * Create new Configuration instance from set values.
     * @return Configuration instance.
     * @throws IllegalStateException if improper values defined.
     */
    public Configuration build() {
        validate();
        return new Configuration(apiToken);
    }
}
