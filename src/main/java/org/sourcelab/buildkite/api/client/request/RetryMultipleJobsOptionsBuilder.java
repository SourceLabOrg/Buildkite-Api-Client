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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class RetryMultipleJobsOptionsBuilder {
    private String organizationSlug = null;
    private String pipelineSlug = null;
    private Long buildNumber = null;
    private Set<String> jobIds = new HashSet<>(;

    public RetryMultipleJobsOptionsBuilder() {
    }

    public RetryMultipleJobsOptionsBuilder withOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
        return this;
    }

    public RetryMultipleJobsOptionsBuilder withPipelineSlug(final String pipelineSlug) {
        this.pipelineSlug = pipelineSlug;
        return this;
    }

    public RetryMultipleJobsOptionsBuilder withBuildNumber(long buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public RetryMultipleJobsOptionsBuilder withJobId(final String jobId) {
        this.jobIds.add(jobId);
        return this;
    }

    public RetryMultipleJobsOptionsBuilder withJobIds(final Collection<String> jobIds) {
        this.jobIds.addAll(jobIds);
        return this;
    }

    /**
     * Create new {@link RetryMultipleJobsOptions} instance.
     * @return Create new {@link RetryMultipleJobsOptions} instance.
     */
    public RetryMultipleJobsOptions build() {
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
        if (jobIds.isEmpty()) {
            throw new BuilderValidationException("At least one JobId must be provided.");
        }
        return new RetryMultipleJobsOptions(organizationSlug, pipelineSlug, buildNumber, jobIds);
    }
}
