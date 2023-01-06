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

import org.sourcelab.buildkite.api.client.http.ClientFactory;

/**
 * User supplied Configuration of the API Client.
 */
public class Configuration {
    private final String apiToken;
    private final String apiUrl;
    private final ClientFactory clientFactory;

    /**
     * Create a new Builder for creating Configuration instances.
     * @return Builder for Configuration instances.
     */
    public static ConfigurationBuilder newBuilder() {
        return new ConfigurationBuilder();
    }

    /**
     * Constructor.  Use {@see Configuration::newBuilder()}
     *
     * @param apiToken Set the configured Api Token.
     * @param apiUrl Set the API Url.
     * @param clientFactory Set the client factory.
     */
    protected Configuration(final String apiToken, final String apiUrl, final ClientFactory clientFactory) {
        this.apiToken = apiToken;
        this.apiUrl = apiUrl;
        this.clientFactory = clientFactory;
    }

    /**
     * The configured Api Token.
     * @return The configured Api Token.
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     * The configured Api Url. Example: "https://api.buildkite.com"
     * @return The configured Api Url. Example: "https://api.buildkite.com"
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * The configured Client factory instance.
     * @return The configured client factory instance.
     */
    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    @Override
    public String toString() {
        return "Configuration{"
                + "apiToken='XXXXXXX'"
                + ", apiUrl='" + apiUrl + '\''
                + ", clientFactory=" + clientFactory
                + '}';
    }
}
