package org.sourcelab.buildkite.api.client.http;

import org.sourcelab.buildkite.api.client.request.Request;

public interface Client {
    HttpResult executeRequest(final Request request);
}
