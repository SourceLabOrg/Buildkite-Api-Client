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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pipeline {
    // TODO Fields
    // skip_queued_branch_builds_filter
    // cancel_running_branch_builds_filter

    private final String id;
    private final String graphqlId;
    private final String url;
    private final String name;
    private final String description;
    private final String slug;
    private final String repository;
    private final String clusterId;

    private final boolean skipQueuedBranchBuilds;
    private final boolean cancelRunningBranchBuilds;
    private final boolean allowRebuilds;

    private final String buildsUrl;
    private final String badgeUrl;

    private final ZonedDateTime createdAt;

    private final long scheduledBuildsCount;
    private final long runningBuildsCount;
    private final long scheduledJobsCount;
    private final long runningJobsCount;
    private final long waitingJobsCount;

    private final Provider provider;
    private final List<Step> steps;

    /**
     * Constructor.
     */
    public Pipeline(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("url") final String url,
        @JsonProperty("name") final String name,
        @JsonProperty("description") final String description,
        @JsonProperty("slug") final String slug,
        @JsonProperty("repository") final String repository,
        @JsonProperty("cluster_id") final String clusterId,
        @JsonProperty("skip_queued_branch_builds") final Boolean skipQueuedBranchBuilds,
        @JsonProperty("cancel_running_branch_builds") final Boolean cancelRunningBranchBuilds,
        @JsonProperty("allow_rebuilds") final Boolean allowRebuilds,
        @JsonProperty("builds_url") final String buildsUrl,
        @JsonProperty("badge_url") final String badgeUrl,
        @JsonProperty("created_at") final ZonedDateTime createdAt,
        @JsonProperty("scheduled_builds_count") final Long scheduledBuildsCount,
        @JsonProperty("running_builds_count") final Long runningBuildsCount,
        @JsonProperty("scheduled_jobs_count") final Long scheduledJobsCount,
        @JsonProperty("running_jobs_count") final Long runningJobsCount,
        @JsonProperty("waiting_jobs_count") final Long waitingJobsCount,
        @JsonProperty("provider") final Provider provider,
        @JsonProperty("steps") final List<Step> steps
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.url = url;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.repository = repository;
        this.clusterId = clusterId;
        this.skipQueuedBranchBuilds = skipQueuedBranchBuilds == null ? false : skipQueuedBranchBuilds;
        this.cancelRunningBranchBuilds = cancelRunningBranchBuilds == null ? false : cancelRunningBranchBuilds;
        this.allowRebuilds = allowRebuilds == null ? false : allowRebuilds;
        this.buildsUrl = buildsUrl;
        this.badgeUrl = badgeUrl;
        this.createdAt = createdAt;
        this.scheduledBuildsCount = scheduledBuildsCount == null ? 0 : scheduledBuildsCount;
        this.runningBuildsCount = runningBuildsCount == null ? 0 : runningBuildsCount;
        this.scheduledJobsCount = scheduledJobsCount == null ? 0 : scheduledJobsCount;
        this.runningJobsCount = runningJobsCount == null ? 0 : runningJobsCount;
        this.waitingJobsCount = waitingJobsCount == null ? 0 : waitingJobsCount;
        this.provider = provider;

        final List<Step> stepList = new ArrayList<>();
        if (steps != null) {
            stepList.addAll(steps);
        }
        this.steps = Collections.unmodifiableList(stepList);
    }

    public String getId() {
        return id;
    }

    public String getGraphqlId() {
        return graphqlId;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getRepository() {
        return repository;
    }

    public boolean isSkipQueuedBranchBuilds() {
        return skipQueuedBranchBuilds;
    }

    public boolean isCancelRunningBranchBuilds() {
        return cancelRunningBranchBuilds;
    }

    public String getBuildsUrl() {
        return buildsUrl;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public long getScheduledBuildsCount() {
        return scheduledBuildsCount;
    }

    public long getRunningBuildsCount() {
        return runningBuildsCount;
    }

    public long getScheduledJobsCount() {
        return scheduledJobsCount;
    }

    public long getRunningJobsCount() {
        return runningJobsCount;
    }

    public long getWaitingJobsCount() {
        return waitingJobsCount;
    }

    public Provider getProvider() {
        return provider;
    }

    public String getDescription() {
        return description;
    }

    public String getClusterId() {
        return clusterId;
    }

    public boolean isAllowRebuilds() {
        return allowRebuilds;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Pipeline{"
            + "id='" + id + '\''
            + ", graphqlId='" + graphqlId + '\''
            + ", url='" + url + '\''
            + ", name='" + name + '\''
            + ", description='" + description + '\''
            + ", slug='" + slug + '\''
            + ", repository='" + repository + '\''
            + ", clusterId='" + clusterId + '\''
            + ", skipQueuedBranchBuilds=" + skipQueuedBranchBuilds
            + ", cancelRunningBranchBuilds=" + cancelRunningBranchBuilds
            + ", allowRebuilds=" + allowRebuilds
            + ", buildsUrl='" + buildsUrl + '\''
            + ", badgeUrl='" + badgeUrl + '\''
            + ", createdAt=" + createdAt
            + ", scheduledBuildsCount=" + scheduledBuildsCount
            + ", runningBuildsCount=" + runningBuildsCount
            + ", scheduledJobsCount=" + scheduledJobsCount
            + ", runningJobsCount=" + runningJobsCount
            + ", waitingJobsCount=" + waitingJobsCount
            + ", provider=" + provider
            + ", steps=" + steps
            + '}';
    }
}
