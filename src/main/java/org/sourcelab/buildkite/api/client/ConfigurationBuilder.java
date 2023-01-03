package org.sourcelab.buildkite.api.client;

public final class ConfigurationBuilder {
    private String apiToken;

    public ConfigurationBuilder() {
    }

    public ConfigurationBuilder withApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    public Configuration build() {
        return new Configuration(apiToken);
    }
}
