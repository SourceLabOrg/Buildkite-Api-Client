package org.sourcelab.buildkite.api.client.response.parser;

import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.PingResponse;

public class PingResponseParser implements ResponseParser<PingResponse> {
    @Override
    public PingResponse parseResponse(final HttpResult result) {
        return new PingResponse("something", 12L);
    }
}
