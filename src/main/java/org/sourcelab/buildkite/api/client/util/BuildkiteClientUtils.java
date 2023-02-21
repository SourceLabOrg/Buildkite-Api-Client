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

package org.sourcelab.buildkite.api.client.util;

import org.sourcelab.buildkite.api.client.BuildkiteClient;
import org.sourcelab.buildkite.api.client.exception.BuildkiteException;
import org.sourcelab.buildkite.api.client.request.AnnotationFilters;
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.Filters;
import org.sourcelab.buildkite.api.client.request.GetAnnotationsForBuildRequest;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.ListOrganizationsRequest;
import org.sourcelab.buildkite.api.client.request.ListPipelinesRequest;
import org.sourcelab.buildkite.api.client.request.OrganizationFilters;
import org.sourcelab.buildkite.api.client.request.PageOptions;
import org.sourcelab.buildkite.api.client.request.PageableRequest;
import org.sourcelab.buildkite.api.client.request.PipelineFilters;
import org.sourcelab.buildkite.api.client.request.Request;
import org.sourcelab.buildkite.api.client.request.RetryJobOptions;
import org.sourcelab.buildkite.api.client.request.RetryMultipleJobsOptions;
import org.sourcelab.buildkite.api.client.response.Job;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.ListOrganizationsResponse;
import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.MultipleRetriedJobsResults;
import org.sourcelab.buildkite.api.client.response.PageableResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection of Utilities for common access patterns that consists of multiple
 * requests to the Buildkite REST Api.
 */
public class BuildkiteClientUtils {

    /**
     * Helper method to retrieve all entries given a filter criteria.
     * The results will be ordered from OLDEST to NEWEST.
     *
     * NOTE: This may result in executing a LARGE number of requests depending
     * on how many entries exist.
     *
     * @param <REQUEST> The request class.
     * @param <OBJECT> The object within the Response to return.
     * @param filters Search criteria.
     * @param requestClass The request class.
     * @param objectClass The object within the Response to return.
     * @param client The BuildkiteClient to execute the requests against.
     * @return List of Objects sorted from OLDEST to NEWEST.
     * @throws BuildkiteException on errors.
     */
    public static <REQUEST, OBJECT> List<OBJECT> retrieveAll(
        final Filters filters,
        final Class<REQUEST> requestClass,
        final Class<OBJECT> objectClass,
        final BuildkiteClient client
    ) {
        final PageableRequest<REQUEST> request;
        if (filters instanceof BuildFilters) {
            request = (PageableRequest<REQUEST>) new ListBuildsRequest((BuildFilters) filters);
        } else if (filters instanceof OrganizationFilters) {
            request = (PageableRequest<REQUEST>) new ListOrganizationsRequest((OrganizationFilters) filters);
        } else if (filters instanceof PipelineFilters) {
            request = (PageableRequest<REQUEST>) new ListPipelinesRequest((PipelineFilters) filters);
        } else if (filters instanceof AnnotationFilters) {
            request = (PageableRequest<REQUEST>) new GetAnnotationsForBuildRequest((AnnotationFilters) filters);
        } else {
            throw new RuntimeException("Unknown type pass " + filters.getClass().getSimpleName());
        }

        final List<OBJECT> entries = new ArrayList<>();
        boolean hasMore = true;
        int page = 0;
        while (hasMore) {
            page++;

            // Create request
            request.updatePageOptions(new PageOptions(page, 100));

            // Retrieve first entry only, to determine how many total entries there are.
            final PageableResponse<REQUEST> lookupResponse = client.executeRequest((Request<? extends PageableResponse<REQUEST>>) request);

            if (filters instanceof BuildFilters) {
                entries.addAll((Collection<? extends OBJECT>) ((ListBuildsResponse) lookupResponse).getBuilds());
            } else if (filters instanceof OrganizationFilters) {
                entries.addAll((Collection<? extends OBJECT>) ((ListOrganizationsResponse) lookupResponse).getOrganizations());
            } else if (filters instanceof PipelineFilters) {
                entries.addAll((Collection<? extends OBJECT>) ((ListPipelinesResponse) lookupResponse).getPipelines());
            } else {
                throw new RuntimeException("Unknown type.");
            }

            hasMore = lookupResponse.getPagingLinks().hasNextUrl();
        }

        // But the results are still sorted in newest to oldest, presumably, we want oldest to newest, so reverse the order
        // before returning.
        final List<OBJECT> reversed = new ArrayList<>(entries);
        return reversed;
    }

    /**
     * Given multiple jobs that belong to the same pipeline, retry all of them and return a single result.
     *
     * @param options Defines the jobs to retry.
     * @param client The client to execute the requests against.
     * @return Results from retrying multiple jobs.
     * @throws BuildkiteException on errors.
     */
    public static MultipleRetriedJobsResults retryMultipleJobs(
        final RetryMultipleJobsOptions options,
        final BuildkiteClient client
    ) {
        final Map<String, Job> updatedJobs = new HashMap<>();
        final Map<String, BuildkiteException> errors = new HashMap<>();

        options.getJobIds().forEach((jobId) -> {
            try {
                final Job retriedJob = client.retryJob(RetryJobOptions.newBuilder()
                    .withJobId(jobId)
                    .withBuildNumber(options.getBuildNumber())
                    .withPipelineSlug(options.getPipelineSlug())
                    .withOrganizationSlug(options.getOrganizationSlug())
                    .build()
                );
                updatedJobs.put(jobId, retriedJob);
            } catch (final BuildkiteException error) {
                if (options.isThrowOnError()) {
                    throw error;
                }
                errors.put(jobId, error);
            }
        });
        return new MultipleRetriedJobsResults(updatedJobs, errors);
    }
}
