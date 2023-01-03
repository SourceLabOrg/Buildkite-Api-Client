package org.sourcelab.buildkite.api.client.request;

abstract public class GetRequest<T> implements Request<T> {
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }
}
