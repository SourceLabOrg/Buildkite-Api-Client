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
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.ListPipelinesRequest;
import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.PagingLinks;
import org.sourcelab.buildkite.api.client.response.Pipeline;

import java.util.Arrays;

public class ListPipelinesResponseParser implements ResponseParser<ListPipelinesResponse> {
    private final ListPipelinesRequest originalRequest;

    public ListPipelinesResponseParser(final ListPipelinesRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public ListPipelinesResponse parseResponse(final HttpResult result) throws JsonProcessingException {
        final PagingLinks pagingLinks;
        if (result.getHttpHeaders().hasHeader("Link")) {
            // Parse out the link header.
            pagingLinks = PagingLinks.newBuilder()
                .fromHeaderLine(result.getHttpHeaders().getHeader("Link"))
                .build();
        } else {
            pagingLinks = PagingLinks.newBuilder().build();
        }

        final Pipeline[] pipelines = JacksonFactory.newInstance().readValue(result.getContent(), Pipeline[].class);

        // Construct response.
        return new ListPipelinesResponse(pagingLinks, Arrays.asList(pipelines), originalRequest);
    }
}
