package org.sourcelab.buildkite.api.client;
public class Configuration {
    private final String apiToken;

    public static ConfigurationBuilder newBuilder() {
        return new ConfigurationBuilder();
    }

    /**
     * Constructor.  Use {@see Configuration::newBuilder()}
     * @param apiToken
     */
    protected Configuration(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }
}
