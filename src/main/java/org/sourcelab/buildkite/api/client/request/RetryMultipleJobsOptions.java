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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Options for Retrying multiple Jobs.
 */
public class RetryMultipleJobsOptions {
    private final String organizationSlug;
    private final String pipelineSlug;
    private final long buildNumber;
    private final Set<String> jobIds;
    private final boolean throwOnError;

    /**
     * Builder instance for {@link RetryMultipleJobsOptions}.
     * @return Builder instance for {@link RetryMultipleJobsOptions}.
     */
    public static RetryMultipleJobsOptionsBuilder newBuilder() {
        return new RetryMultipleJobsOptionsBuilder();
    }

    /**
     * Constructor.
     */
    public RetryMultipleJobsOptions(
        final String organizationSlug,
        final String pipelineIdSlug,
        final long buildNumber,
        final Collection<String> jobIds,
        final boolean throwOnError
    ) {
        this.organizationSlug = organizationSlug;
        this.pipelineSlug = pipelineIdSlug;
        this.buildNumber = buildNumber;
        this.jobIds = new HashSet<>(jobIds);
        this.throwOnError = throwOnError;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public String getPipelineSlug() {
        return pipelineSlug;
    }

    public long getBuildNumber() {
        return buildNumber;
    }

    public Set<String> getJobIds() {
        return jobIds;
    }

    /**
     * If true, if any single request fails, it will not process the remaining requests and immediately
     * throw an exception.
     *
     * If false, any errors will be captured by the return result, and further requests will continue.
     *
     * @return If true, errors will be raised as they occur.  If false, errors will be captured in the resulting
     *         object returned.
     */
    public boolean isThrowOnError() {
        return throwOnError;
    }

    @Override
    public String toString() {
        return "RetryMultipleJobsOptions{"
            + "\n\torganizationSlug='" + organizationSlug + '\''
            + "\n\tpipelineSlug='" + pipelineSlug + '\''
            + "\n\tbuildNumber=" + buildNumber
            + "\n\tjobIds=" + jobIds
            + "\n\tthrowOnError=" + throwOnError
            + "\n}";
    }
}
