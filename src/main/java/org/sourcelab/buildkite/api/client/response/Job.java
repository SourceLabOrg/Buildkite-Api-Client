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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class Job {
    private final String id;
    private final String graphqlId;
    private final String type;
    private final String name;
    private final String stepKey;
    private final String state;
    private final String webUrl;
    private final String logUrl;
    private final String rawLogUrl;
    private final String command;

    private final boolean softFailed;
    private final int exitStatus;
    private final String artifactPaths;

    private final Agent agent;

    private final ZonedDateTime createdAt;
    private final ZonedDateTime scheduledAt;
    private final ZonedDateTime runnableAt;
    private final ZonedDateTime startedAt;
    private final ZonedDateTime finishedAt;

    private final boolean retried;
    private final String retriedInJobId;
    private final long retriesCount;
    private final String retryType;

    // Parallel Builds
    private final Integer parallelGroupIndex;
    private final Integer parallelGroupTotal;

    // TODO
    // "agent_query_rules": ["*"],

    /**
     * Constructor.
     */
    @JsonCreator
    public Job(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("type") final String type,
        @JsonProperty("name") final String name,
        @JsonProperty("step_key") final String stepKey,
        @JsonProperty("state") final String state,
        @JsonProperty("web_url") final String webUrl,
        @JsonProperty("log_url") final String logUrl,
        @JsonProperty("raw_log_url") final String rawLogUrl,
        @JsonProperty("command") final String command,
        @JsonProperty("soft_failed") final Boolean softFailed,
        @JsonProperty("exit_status") final int exitStatus,
        @JsonProperty("artifact_paths") final String artifactPaths,
        @JsonProperty("agent") final Agent agent,
        @JsonProperty("created_at") final ZonedDateTime createdAt,
        @JsonProperty("scheduled_at") final ZonedDateTime scheduledAt,
        @JsonProperty("runnable_at") final ZonedDateTime runnableAt,
        @JsonProperty("started_at") final ZonedDateTime startedAt,
        @JsonProperty("finished_at") final ZonedDateTime finishedAt,
        @JsonProperty("retried") final Boolean retried,
        @JsonProperty("retried_in_job_id") final String retriedInJobId,
        @JsonProperty("retries_count") final Long retriesCount,
        @JsonProperty("retry_type") final String retryType,
        @JsonProperty("parallel_group_index") final Integer parallelGroupIndex,
        @JsonProperty("parallel_group_total") final Integer parallelGroupTotal
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.type = type;
        this.name = name;
        this.stepKey = stepKey;
        this.state = state;
        this.webUrl = webUrl;
        this.logUrl = logUrl;
        this.rawLogUrl = rawLogUrl;
        this.command = command;
        this.softFailed = softFailed == null ? false : softFailed;
        this.exitStatus = exitStatus;
        this.artifactPaths = artifactPaths;
        this.agent = agent;
        this.createdAt = createdAt;
        this.scheduledAt = scheduledAt;
        this.runnableAt = runnableAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.retried = retried == null ? false : retried;
        this.retriedInJobId = retriedInJobId;
        this.retriesCount = retriesCount == null ? 0 : retriesCount;
        this.retryType = retryType;
        this.parallelGroupIndex = parallelGroupIndex;
        this.parallelGroupTotal = parallelGroupTotal;
    }

    public String getId() {
        return id;
    }

    public String getGraphqlId() {
        return graphqlId;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStepKey() {
        return stepKey;
    }

    public String getState() {
        return state;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public String getRawLogUrl() {
        return rawLogUrl;
    }

    public String getCommand() {
        return command;
    }

    public boolean isSoftFailed() {
        return softFailed;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public String getArtifactPaths() {
        return artifactPaths;
    }

    public Agent getAgent() {
        return agent;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public ZonedDateTime getRunnableAt() {
        return runnableAt;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public boolean isRetried() {
        return retried;
    }

    public String getRetriedInJobId() {
        return retriedInJobId;
    }

    public long getRetriesCount() {
        return retriesCount;
    }

    public String getRetryType() {
        return retryType;
    }

    public Integer getParallelGroupIndex() {
        return parallelGroupIndex;
    }

    public Integer getParallelGroupTotal() {
        return parallelGroupTotal;
    }

    @Override
    public String toString() {
        return "Job{"
            + "\n\tid='" + id + '\''
            + "\n\tgraphqlId='" + graphqlId + '\''
            + "\n\ttype='" + type + '\''
            + "\n\tname='" + name + '\''
            + "\n\tstepKey='" + stepKey + '\''
            + "\n\tstate='" + state + '\''
            + "\n\twebUrl='" + webUrl + '\''
            + "\n\tlogUrl='" + logUrl + '\''
            + "\n\trawLogUrl='" + rawLogUrl + '\''
            + "\n\tcommand='" + command + '\''
            + "\n\tsoftFailed=" + softFailed
            + "\n\texitStatus=" + exitStatus
            + "\n\tartifactPaths='" + artifactPaths + '\''
            + "\n\tagent=" + agent
            + "\n\tcreatedAt=" + createdAt
            + "\n\tscheduledAt=" + scheduledAt
            + "\n\trunnableAt=" + runnableAt
            + "\n\tstartedAt=" + startedAt
            + "\n\tfinishedAt=" + finishedAt
            + "\n\tretried=" + retried
            + "\n\tretriedInJobId='" + retriedInJobId + '\''
            + "\n\tretriesCount=" + retriesCount
            + "\n\tretryType='" + retryType + '\''
            + "\n\tparallelGroupIndex=" + parallelGroupIndex
            + "\n\tparallelGroupTotal=" + parallelGroupTotal
            + "\n}";
    }
}
