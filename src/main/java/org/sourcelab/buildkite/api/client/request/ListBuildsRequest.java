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

import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.parser.ListBuildsResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class ListBuildsRequest extends GetRequest<ListBuildsResponse> {
    private final BuildFilters filters;

    public ListBuildsRequest(final BuildFilters filters) {
        Objects.requireNonNull(filters);
        this.filters = filters;
    }

    public String getPath() {
        return "/v2/builds";
    }

    @Override
    public RequestParameters getRequestParameters() {
        final RequestParametersBuilder builder = RequestParameters.newBuilder();

        // Paging options
        if (filters.getPageOptions() != null) {
            builder.withParameter("per_page", filters.getPageOptions().getPerPage());
            builder.withParameter("page", filters.getPageOptions().getPage());
        }

        // If we have branches
        if (!filters.getBranches().isEmpty()) {
            builder.withParameter("branch", filters.getBranches());
        }

        // If we have commits
        if (!filters.getCommits().isEmpty()) {
            builder.withParameter("commit", filters.getCommits());
        }

        // If we have states
        if (!filters.getStates().isEmpty()) {
            builder.withParameter("state", filters.getStates());
        }

        // Creator filter
        if (filters.getCreator() != null) {
            builder.withParameter("creator", filters.getCreator());
        }

        // include_retried_jobs
        if (filters.getIncludeRetriedJobs() != null) {
            final String booleanStr = filters.getIncludeRetriedJobs() ? "true" : "false";
            builder.withParameter("include_retried_jobs", booleanStr);
        }

        // Metadata fields
        for (final Map.Entry<String, String> entry : filters.getMetaData().entrySet()) {
            builder.withParameter("metadata[" + entry.getKey() + "]", entry.getValue());
        }

        // Date Filters
        if (filters.getCreatedFrom() != null) {
            builder.withParameter("created_from", filters.getCreatedFrom().format(DateTimeFormatter.ISO_DATE));
        }
        if (filters.getCreatedTo() != null) {
            builder.withParameter("created_to", filters.getCreatedTo().format(DateTimeFormatter.ISO_DATE));
        }
        if (filters.getFinishedFrom() != null) {
            builder.withParameter("finished_from", filters.getFinishedFrom().format(DateTimeFormatter.ISO_DATE));
        }
        return builder.build();
    }

    @Override
    public ResponseParser<ListBuildsResponse> getResponseParser() {
        return new ListBuildsResponseParser();
    }
}
