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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Options for Retrying a Job.
 */
public class UnblockJobOptions {
    // Required
    private final String organizationSlug;
    private final String pipelineSlug;
    private final long buildNumber;
    private final String jobId;

    // Optional
    private final String unblockerUserId;
    private final Map<String, String> fields;

    /**
     * Builder instance for {@link UnblockJobOptions}.
     * @return Builder instance for {@link UnblockJobOptions}.
     */
    public static RetryJobOptionsBuilder newBuilder() {
        return new RetryJobOptionsBuilder();
    }

    /**
     * Constructor.
     */
    public UnblockJobOptions(
        final String organizationSlug,
        final String pipelineIdSlug,
        final long buildNumber,
        final String jobId,
        final String unblockerUserId,
        final Map<String, String> fields
    ) {
        this.organizationSlug = organizationSlug;
        this.pipelineSlug = pipelineIdSlug;
        this.buildNumber = buildNumber;
        this.jobId = jobId;

        // Optional Fields.
        this.unblockerUserId = unblockerUserId;

        final Map<String, String> fieldsMap = new HashMap<>();
        if (fields != null) {
            fieldsMap.putAll(fields);
        }
        this.fields = Collections.unmodifiableMap(fieldsMap);
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

    public String getJobId() {
        return jobId;
    }

    public boolean hasUnblockerUserId() {
        return unblockerUserId != null;
    }

    public String getUnblockerUserId() {
        return unblockerUserId;
    }

    public boolean hasFields() {
        return fields.size() > 0;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "UnblockJobOptions{"
            + "organizationSlug='" + organizationSlug + '\''
            + ", pipelineSlug='" + pipelineSlug + '\''
            + ", buildNumber=" + buildNumber
            + ", jobId='" + jobId + '\''
            + ", unblockerUserId='" + unblockerUserId + '\''
            + ", fields=" + fields
            + '}';
    }
}
