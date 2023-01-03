package org.sourcelab.buildkite.api.client.request;

import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

public interface Request<T> {
    HttpMethod getMethod();
    String getPath();
    ResponseParser<T> getResponseParser();

    default T parseResponse(final HttpResult result) {
        return getResponseParser().parseResponse(result);
    }
}
