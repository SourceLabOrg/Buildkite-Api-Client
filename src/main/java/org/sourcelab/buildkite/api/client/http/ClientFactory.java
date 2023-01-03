package org.sourcelab.buildkite.api.client.http;

import org.sourcelab.buildkite.api.client.Configuration;

public interface ClientFactory {
    Client createClient(final Configuration configuration);
}
