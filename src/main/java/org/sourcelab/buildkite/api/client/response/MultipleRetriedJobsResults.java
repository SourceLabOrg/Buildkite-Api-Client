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

package org.sourcelab.buildkite.api.client.response;

import org.sourcelab.buildkite.api.client.BuildkiteClient;
import org.sourcelab.buildkite.api.client.exception.BuildkiteException;
import org.sourcelab.buildkite.api.client.request.RetryMultipleJobsOptions;
import org.sourcelab.buildkite.api.client.util.BuildkiteClientUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Results set from retrying multiple jobs.
 * {@link BuildkiteClientUtils#retryMultipleJobs(RetryMultipleJobsOptions, BuildkiteClient)}
 */
public class MultipleRetriedJobsResults {
    /**
     * Maps Original Job Id => The new retried job instance.
     */
    private final Map<String,Job> originalJobIdsMappedToUpdatedJobs;

    /**
     * Maps Original Job Id => The new retried job instance.
     */
    private final Map<String, BuildkiteException> originalJobIdsMappedToErrors;

    /**
     * Constructor.
     *
     * @param originalJobIdsMappedToUpdatedJobs Original Job Id mapped to the updated Job.
     * @param originalJobIdsMappedToErrors Original Job Id mapped to any error that occurred during the request.
     */
    public MultipleRetriedJobsResults(
        final Map<String, Job> originalJobIdsMappedToUpdatedJobs,
        final Map<String, BuildkiteException> originalJobIdsMappedToErrors
    ) {
        this.originalJobIdsMappedToUpdatedJobs = Collections.unmodifiableMap(new HashMap<>(originalJobIdsMappedToUpdatedJobs));
        this.originalJobIdsMappedToErrors = Collections.unmodifiableMap(new HashMap<>(originalJobIdsMappedToErrors));
    }

    /**
     * Ids of the jobs that were retried.
     * @return Ids of the jobs that were retried.
     */
    public Set<String> getOriginalJobIds() {
        // Merge keys from both sets.
        final Set<String> jobIds = new HashSet<>(originalJobIdsMappedToUpdatedJobs.keySet());
        jobIds.addAll(originalJobIdsMappedToErrors.keySet());
        return jobIds;
    }

    /**
     * Check of a Retried job instance exists for the original job id.
     * @param originalJobId Id of the job that was retried.
     * @return true if an instance exists, false if not.
     */
    public boolean hasRetriedJob(final String originalJobId) {
        return originalJobIdsMappedToUpdatedJobs.containsKey(originalJobId);
    }

    /**
     * Get the Retried job instance by the original job id.
     * @param originalJobId Id of the job that was retried.
     * @return The new Job instance.
     */
    public Job getRetriedJobByOriginalJobId(final String originalJobId) {
        if (!hasRetriedJob(originalJobId)) {
            throw new IllegalArgumentException("Job Id " + originalJobId + " was not retried and has no result.");
        }
        return originalJobIdsMappedToUpdatedJobs.get(originalJobId);
    }

    /**
     * Original Job Id mapped to the new retried job instance.
     * @return Original Job Id mapped to the new retried job instance.
     */
    public Map<String, Job> getOriginalJobIdsMappedToUpdatedJobs() {
        return originalJobIdsMappedToUpdatedJobs;
    }

    /**
     * All of the new retried job instances.
     * @return All of the new retried job instances.
     */
    public List<Job> getRetriedJobs() {
        return new ArrayList<>(originalJobIdsMappedToUpdatedJobs.values());
    }

    /**
     * Check of a given Job id had an error.
     * @param originalJobId Id of the job that was retried.
     * @return true if an error occurred/exists, false if not.
     */
    public boolean didJobHaveError(final String originalJobId) {
        return originalJobIdsMappedToErrors.containsKey(originalJobId);
    }

    /**
     * Get the error associated with the original job id.
     * @param originalJobId Id of the job that was retried.
     * @return The error that occurred.
     */
    public BuildkiteException getErrorByOriginalJobId(final String originalJobId) {
        if (!didJobHaveError(originalJobId)) {
            throw new IllegalArgumentException("Job Id " + originalJobId + " did not have an error.");
        }
        return originalJobIdsMappedToErrors.get(originalJobId);
    }

    /**
     * Original Job Id mapped to the error associated with it.
     * @return Original Job Id mapped to the error associated with it.
     */
    public Map<String, BuildkiteException> getOriginalJobIdsMappedToErrors() {
        return originalJobIdsMappedToErrors;
    }

    /**
     * All the errors.
     * @return All the errors.
     */
    public List<BuildkiteException> getErrors() {
        return new ArrayList<>(originalJobIdsMappedToErrors.values());
    }

    @Override
    public String toString() {
        return "MultipleRetriedJobsResults{"
            + "\n\toriginalJobIdsMappedToUpdatedJobs=" + originalJobIdsMappedToUpdatedJobs
            + "\n\toriginalJobIdsMappedToErrors=" + originalJobIdsMappedToErrors
            + "\n}";
    }
}
