package org.sourcelab.buildkite.api.client.http;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.sourcelab.buildkite.api.client.Configuration;
import org.sourcelab.buildkite.api.client.request.Request;

import java.io.IOException;

public class HttpComponentsClient implements Client {
    private Configuration configuration;

    public HttpComponentsClient(final Configuration configuration) {
        this.configuration = configuration;
    }

    private CloseableHttpClient getClient() {
        final HttpClientBuilder builder = HttpClientBuilder.create();
        return builder.build();
    }

    public HttpResult executeRequest(final Request request) {
        try (final CloseableHttpClient httpClient = getClient()) {
            final HttpResult result;
            switch (request.getMethod()) {
                case GET:
                    return executeGetRequest(request, httpClient);
                default:
                    throw new IllegalArgumentException("Invalid HttpType");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResult executeGetRequest(final Request request, final CloseableHttpClient httpClient) {
        final String path = configuration.getApiUrl() + request.getPath();
        final HttpGet httpGet = new HttpGet(path);

        try (final CloseableHttpResponse response = httpClient.execute(httpGet)) {
            final HttpEntity entity = response.getEntity();
            final HttpResult result = new HttpResult(response.getCode(), EntityUtils.toString(entity));
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
