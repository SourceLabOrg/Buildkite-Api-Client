package org.sourcelab.buildkite.api.client.response.parser;

import org.sourcelab.buildkite.api.client.http.HttpResult;

public class StringResponse implements ResponseParser<String> {
    @Override
    public String parseResponse(final HttpResult result) {
        return result.getContent();
    }
}
