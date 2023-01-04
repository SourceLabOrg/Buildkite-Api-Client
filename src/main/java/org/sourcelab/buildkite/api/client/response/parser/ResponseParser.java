package org.sourcelab.buildkite.api.client.response.parser;

import org.sourcelab.buildkite.api.client.http.HttpResult;

import java.io.IOException;

public interface ResponseParser<T> {
    T parseResponse(final HttpResult result) throws IOException;
}
