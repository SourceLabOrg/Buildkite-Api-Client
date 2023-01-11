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

/**
 * Optional Properties to filter Builds.
 */
public class GetBuildFilters {
    private final String orgIdSlug;
    private final String pipelineIdSlug;
    private final long buildNumber;
    private final Boolean includeRetriedJobs;

    /**
     * Builder for {@link GetBuildFilters}.
     * @return Builder for {@link GetBuildFilters}.
     */
    public static GetBuildFiltersBuilder newBuilder() {
        return new GetBuildFiltersBuilder();
    }

    /**
     * Constructor.
     * Use {@link BuildFiltersBuilder} to create instances.
     */
    public GetBuildFilters(
        final String orgIdSlug,
        final String pipelineIdSlug,
        final long buildNumber,
        final Boolean includeRetriedJobs
    ) {
        this.orgIdSlug = orgIdSlug;
        this.pipelineIdSlug = pipelineIdSlug;
        this.buildNumber = buildNumber;
        this.includeRetriedJobs = includeRetriedJobs;
    }

    public String getOrgIdSlug() {
        return orgIdSlug;
    }

    public String getPipelineIdSlug() {
        return pipelineIdSlug;
    }

    public long getBuildNumber() {
        return buildNumber;
    }

    public Boolean getIncludeRetriedJobs() {
        return includeRetriedJobs;
    }

    @Override
    public String toString() {
        return "GetBuildFilters{"
                + "orgIdSlug='" + orgIdSlug + '\''
                + ", pipelineIdSlug='" + pipelineIdSlug + '\''
                + ", buildNumber=" + buildNumber
                + ", includeRetriedJobs=" + includeRetriedJobs
                + '}';
    }
}
