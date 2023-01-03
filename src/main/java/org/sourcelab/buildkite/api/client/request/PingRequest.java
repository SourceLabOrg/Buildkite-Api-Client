package org.sourcelab.buildkite.api.client.request;

import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.StringResponse;

public class PingRequest extends GetRequest<String> {
    public String getPath() {
        return "/";
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return new StringResponse();
    }
}
