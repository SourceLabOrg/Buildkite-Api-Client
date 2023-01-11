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

import org.sourcelab.buildkite.api.client.exception.BuilderValidationException;

import java.util.Objects;

public final class GetBuildFiltersBuilder {
    private String orgIdSlug = null;
    private String pipelineIdSlug = null;
    private Long buildNumber = null;
    private Boolean includeRetriedJobs = null;

    public GetBuildFiltersBuilder() {
    }

    public GetBuildFiltersBuilder withOrgIdSlug(final String orgIdSlug) {
        this.orgIdSlug = Objects.requireNonNull(orgIdSlug);
        return this;
    }

    public GetBuildFiltersBuilder withPipelineIdSlug(final String pipelineIdSlug) {
        this.pipelineIdSlug = Objects.requireNonNull(pipelineIdSlug);
        return this;
    }

    public GetBuildFiltersBuilder withBuildNumber(long buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public GetBuildFiltersBuilder withIncludeRetriedJobs(boolean includeRetriedJobs) {
        this.includeRetriedJobs = includeRetriedJobs;
        return this;
    }

    /**
     * Create instance from Builder.
     * @return GetBuildFilters
     * @throws BuilderValidationException if not valid or complete.
     */
    public GetBuildFilters build() {
        // Validate
        if (pipelineIdSlug == null) {
            throw new BuilderValidationException("Pipeline must be provided.");
        }
        if (orgIdSlug == null) {
            throw new BuilderValidationException("Organization must be provided.");
        }
        if (buildNumber == null) {
            throw new BuilderValidationException("Build Number must be provided.");
        }
        return new GetBuildFilters(orgIdSlug, pipelineIdSlug, buildNumber, includeRetriedJobs);
    }
}
