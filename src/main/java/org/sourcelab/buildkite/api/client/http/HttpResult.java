package org.sourcelab.buildkite.api.client.http;

public class HttpResult {
    final int status;
    final String content;

    public HttpResult(final int status, final String content) {
        this.status = status;
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}
