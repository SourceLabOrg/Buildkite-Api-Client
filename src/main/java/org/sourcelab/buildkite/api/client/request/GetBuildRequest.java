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

import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.parser.GetBuildResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.util.Objects;

public class GetBuildRequest extends GetRequest<Build> {
    private final GetBuildFilters filters;

    /**
     * Constructor.
     */
    public GetBuildRequest(final GetBuildFilters filters) {
        this.filters = Objects.requireNonNull(filters);
    }

    @Override
    public String getPath() {
        return "/v2/organizations/" + filters.getOrgIdSlug()
            + "/pipelines/" + filters.getPipelineIdSlug()
            + "/builds/" + filters.getBuildNumber();
    }

    /**
     * KeyValue pairs of Request Parameters.
     * @return RequestParameters associated with request.
     */
    @Override
    public RequestParameters getRequestParameters() {
        final RequestParametersBuilder builder = RequestParameters.newBuilder();
        if (filters.getIncludeRetriedJobs() != null) {
            if (filters.getIncludeRetriedJobs()) {
                builder.withParameter("include_retried_jobs", "true");
            } else {
                builder.withParameter("include_retried_jobs", "false");
            }
        }
        return builder.build();
    }

    @Override
    public ResponseParser<Build> getResponseParser() {
        return new GetBuildResponseParser();
    }
}
