package org.sourcelab.buildkite.api.client.response.parser;

import org.sourcelab.buildkite.api.client.http.HttpResult;

public interface ResponseParser<T> {
    T parseResponse(final HttpResult result);
}
