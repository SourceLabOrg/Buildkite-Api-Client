package org.sourcelab.buildkite.api.client.request;

import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.io.IOException;

public interface Request<T> {
    HttpMethod getMethod();
    String getPath();
    ResponseParser<T> getResponseParser();

    default T parseResponse(final HttpResult result) {
        try {
            return getResponseParser().parseResponse(result);
        } catch (final IOException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }
}
