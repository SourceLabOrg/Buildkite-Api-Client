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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Results set from retrying multiple jobs.
 */
public class MultipleRetriedJobsResults {
    private final Map<String,Job> originalJobIdsMappedToUpdatedJobs;

    /**
     * Constructor.
     * @param originalJobIdsMappedToUpdatedJobs Original Job Id mapped to the updated Job.
     */
    public MultipleRetriedJobsResults(final Map<String, Job> originalJobIdsMappedToUpdatedJobs) {
        this.originalJobIdsMappedToUpdatedJobs = Collections.unmodifiableMap(new HashMap<>(originalJobIdsMappedToUpdatedJobs));
    }

    /**
     * Ids of the jobs that were retried.
     * @return Ids of the jobs that were retried.
     */
    public Set<String> getOriginalJobIds() {
        return originalJobIdsMappedToUpdatedJobs.keySet();
    }

    /**
     * Get the Retried job instance by the original job id.
     * @param originalJobId Id of the job that was retried.
     * @return The new Job instance.
     */
    public Job getRetriedJobByOriginalJobId(final String originalJobId) {
        if (!originalJobIdsMappedToUpdatedJobs.containsKey(originalJobId)) {
            throw new IllegalArgumentException("Job Id " + originalJobId + " was not retried and has no result");
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
     * Original Job Id mapped to the new retried job instance.
     * @return Original Job Id mapped to the new retried job instance.
     */
    public List<Job> getRetriedJobs() {
        return new ArrayList<>(originalJobIdsMappedToUpdatedJobs.values());
    }

    @Override
    public String toString() {
        return "MultipleRetriedJobsResults{"
            + "\n\toriginalJobIdsMappedToUpdatedJobs=" + originalJobIdsMappedToUpdatedJobs
            + "\n}";
    }
}
