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

public final class RetryJobOptionsBuilder {
    private String organizationSlug = null;
    private String pipelineSlug = null;
    private Long buildNumber = null;
    private String jobId = null;

    public RetryJobOptionsBuilder() {
    }

    public RetryJobOptionsBuilder withOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
        return this;
    }

    public RetryJobOptionsBuilder withPipelineSlug(final String pipelineSlug) {
        this.pipelineSlug = pipelineSlug;
        return this;
    }

    public RetryJobOptionsBuilder withBuildNumber(long buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public RetryJobOptionsBuilder withJobId(final String jobId) {
        this.jobId = jobId;
        return this;
    }

    /**
     * Create new {@link RetryJobOptions} instance.
     * @return Create new {@link RetryJobOptions} instance.
     */
    public RetryJobOptions build() {
        // Validation
        if (organizationSlug == null) {
            throw new BuilderValidationException("Organization must be provided.");
        }
        if (pipelineSlug == null) {
            throw new BuilderValidationException("Pipeline must be provided.");
        }
        if (buildNumber == null) {
            throw new BuilderValidationException("BuildNumber must be provided.");
        }
        if (jobId == null) {
            throw new BuilderValidationException("JobId must be provided.");
        }
        return new RetryJobOptions(organizationSlug, pipelineSlug, buildNumber, jobId);
    }
}
