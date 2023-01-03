package org.sourcelab.buildkite.api.client.http;

import org.sourcelab.buildkite.api.client.Configuration;

public class DefaultClientFactory implements ClientFactory {
    @Override
    public Client createClient(final Configuration configuration) {
        return new HttpComponentsClient(configuration);
    }
}
