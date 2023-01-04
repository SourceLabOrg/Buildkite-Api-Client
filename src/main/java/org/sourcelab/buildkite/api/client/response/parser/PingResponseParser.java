package org.sourcelab.buildkite.api.client.response.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.PingResponse;

public class PingResponseParser implements ResponseParser<PingResponse> {
    @Override
    public PingResponse parseResponse(final HttpResult result) throws JsonProcessingException {
        return JacksonFactory.newInstance().readValue(result.getContent(), PingResponse.class);
    }
}
