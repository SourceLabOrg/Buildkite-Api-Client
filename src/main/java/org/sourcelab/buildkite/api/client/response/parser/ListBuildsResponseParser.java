/**
 * Copyright 2023 SourceLab.org https://github.com/SourceLabOrg/Buildkite-Api-Client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.buildkite.api.client.response.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.PagingLinks;

import java.util.Arrays;

public class ListBuildsResponseParser implements ResponseParser<ListBuildsResponse> {
    private final ListBuildsRequest originalRequest;

    public ListBuildsResponseParser(final ListBuildsRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public ListBuildsResponse parseResponse(final HttpResult result) throws JsonProcessingException {
        final PagingLinks pagingLinks;
        if (result.getHttpHeaders().hasHeader("Link")) {
            // Parse out the link header.
            pagingLinks = PagingLinks.newBuilder()
                .fromHeaderLine(result.getHttpHeaders().getHeader("Link"))
                .build();
        } else {
            pagingLinks = PagingLinks.newBuilder().build();
        }

        final Build[] builds = JacksonFactory.newInstance().readValue(result.getContent(), Build[].class);

        // Construct response.
        return new ListBuildsResponse(pagingLinks, Arrays.asList(builds), originalRequest);
    }
}
