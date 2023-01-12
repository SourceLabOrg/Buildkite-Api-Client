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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class UnblockJobOptionsBuilder {
    // Required
    private String organizationSlug = null;
    private String pipelineSlug = null;
    private Long buildNumber = null;
    private String jobId = null;

    // Optional
    private String unblockerUserId = null;
    private Map<String, String> fields = new HashMap<>();

    public UnblockJobOptionsBuilder withOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
        return this;
    }

    public UnblockJobOptionsBuilder withPipelineSlug(final String pipelineSlug) {
        this.pipelineSlug = pipelineSlug;
        return this;
    }

    public UnblockJobOptionsBuilder withBuildNumber(long buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public UnblockJobOptionsBuilder withJobId(final String jobId) {
        this.jobId = jobId;
        return this;
    }

    public UnblockJobOptionsBuilder withUnblocker(final String userId) {
        this.unblockerUserId = userId;
        return this;
    }

    /**
     * Add a field properties.
     * @param fields Fields to be added.
     * @return UnblockJobOptionsBuilder for method chaining.
     */
    public UnblockJobOptionsBuilder withFields(final Map<String,String> fields) {
        Objects.requireNonNull(fields);
        this.fields.putAll(fields);
        return this;
    }

    /**
     * Add a field property.
     * @param fieldName Name of the field.
     * @param fieldValue Value of the field.
     * @return UnblockJobOptionsBuilder for method chaining.
     */
    public UnblockJobOptionsBuilder withField(final String fieldName, final String fieldValue) {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(fieldValue);
        this.fields.put(fieldName, fieldValue);
        return this;
    }

    /**
     * Create new {@link UnblockJobOptions} instance.
     * @return Create new {@link UnblockJobOptions} instance.
     */
    public UnblockJobOptions build() {
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
        return new UnblockJobOptions(organizationSlug, pipelineSlug, buildNumber, jobId, unblockerUserId, fields);
    }
}
