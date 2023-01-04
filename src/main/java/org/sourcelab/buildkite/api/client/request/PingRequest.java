package org.sourcelab.buildkite.api.client.request;

import org.sourcelab.buildkite.api.client.response.PingResponse;
import org.sourcelab.buildkite.api.client.response.parser.PingResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

public class PingRequest extends GetRequest<PingResponse> {
    public String getPath() {
        return "/";
    }

    @Override
    public ResponseParser<PingResponse> getResponseParser() {
        return new PingResponseParser();
    }
}
