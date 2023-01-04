package org.sourcelab.buildkite.api.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PingResponse {
    private final String message;
    private final long timestamp;

    /**
     * Constructor.
     * @param message Message value.
     * @param timestamp Timestamp value.
     */
    @JsonCreator
    public PingResponse(@JsonProperty("message") final String message, @JsonProperty("timestamp") final long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "PingResponse{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
