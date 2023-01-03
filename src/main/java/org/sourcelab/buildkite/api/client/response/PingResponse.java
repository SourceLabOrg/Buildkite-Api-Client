package org.sourcelab.buildkite.api.client.response;

public class PingResponse {
    private final String message;
    private final long timestamp;

    public PingResponse(final String message, final long timestamp) {
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
