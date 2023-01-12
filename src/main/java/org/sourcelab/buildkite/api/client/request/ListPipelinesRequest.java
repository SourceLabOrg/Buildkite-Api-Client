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

package org.sourcelab.buildkite.api.client.request;

import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.parser.ListPipelinesResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.util.Objects;

public class ListPipelinesRequest extends GetRequest<ListPipelinesResponse> implements PageableRequest<ListPipelinesResponse> {
    private final PipelineFilters filters;
    private PageOptions pageOptions;

    /**
     * Constructor.
     * @param filters Search Criteria.
     */
    public ListPipelinesRequest(final PipelineFilters filters) {
        Objects.requireNonNull(filters);
        this.filters = filters;
        this.pageOptions = filters.getPageOptions() == null ? PageOptions.getDefault() : filters.getPageOptions();
    }

    @Override
    public String getPath() {
        return "/v2/organizations/" + filters.getOrgIdSlug() + "/pipelines";
    }

    @Override
    public RequestParameters getRequestParameters() {
        final RequestParametersBuilder builder = RequestParameters.newBuilder();

        // Paging options
        builder.withParameter("per_page", pageOptions.getPerPage());
        builder.withParameter("page", pageOptions.getPage());

        return builder.build();
    }

    @Override
    public ResponseParser<ListPipelinesResponse> getResponseParser() {
        return new ListPipelinesResponseParser(this);
    }

    @Override
    public void updatePageOptions(final PageOptions pageOptions) {
        this.pageOptions = Objects.requireNonNull(pageOptions);
    }
}
