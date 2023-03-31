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
import java.util.List;

public class Build {
    private final String id;
    private final String graphqlId;
    private final String url;
    private final String webUrl;
    private final long number;
    private final BuildState state;
    private final boolean blocked;
    private final String message;
    private final String commit;
    private final String branch;
    private final String source;

    private final ZonedDateTime createdAt;
    private final  ZonedDateTime scheduledAt;
    private final  ZonedDateTime startedAt;
    private final  ZonedDateTime finishedAt;

    // Dependencies
    private final Author author;
    private final Creator creator;
    private final Pipeline pipeline;
    private final List<Job> jobs;
    private final RebuiltFrom rebuiltFrom;
    private final PullRequest pullRequest;

    /**
     * Constructor.
     */
    @JsonCreator
    public Build(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("url") final String url,
        @JsonProperty("web_url") final String webUrl,
        @JsonProperty("number") final long number,
        @JsonProperty("state") final String state,
        @JsonProperty("blocked") final boolean blocked,
        @JsonProperty("message") final String message,
        @JsonProperty("commit") final String commit,
        @JsonProperty("branch") final String branch,
        @JsonProperty("source") final String source,
        @JsonProperty("created_at") final ZonedDateTime createdAt,
        @JsonProperty("scheduled_at") final ZonedDateTime scheduledAt,
        @JsonProperty("started_at") final ZonedDateTime startedAt,
        @JsonProperty("finished_at") final ZonedDateTime finishedAt,
        @JsonProperty("author") final Author author,
        @JsonProperty("creator") final Creator creator,
        @JsonProperty("pipeline") final Pipeline pipeline,
        @JsonProperty("jobs") final List<Job> jobs,
        @JsonProperty("rebuilt_from") final RebuiltFrom rebuiltFrom,
        @JsonProperty("pull_request") final PullRequest pullRequest
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.url = url;
        this.webUrl = webUrl;
        this.number = number;
        this.state = BuildState.createFromString(state);
        this.blocked = blocked;
        this.message = message;
        this.commit = commit;
        this.branch = branch;
        this.source = source;
        this.createdAt = createdAt;
        this.scheduledAt = scheduledAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.author = author;
        this.creator = creator;
        this.pipeline = pipeline;
        this.jobs = jobs;
        this.rebuiltFrom = rebuiltFrom;
        this.pullRequest = pullRequest;
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

    public String getWebUrl() {
        return webUrl;
    }

    public long getNumber() {
        return number;
    }

    public BuildState getState() {
        return state;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public String getMessage() {
        return message;
    }

    public String getCommit() {
        return commit;
    }

    public String getBranch() {
        return branch;
    }

    public String getSource() {
        return source;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public Author getAuthor() {
        return author;
    }

    public Creator getCreator() {
        return creator;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public RebuiltFrom getRebuiltFrom() {
        return rebuiltFrom;
    }

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    @Override
    public String toString() {
        return "Build{"
            + "\n\tid='" + id + '\''
            + "\n\tgraphqlId='" + graphqlId + '\''
            + "\n\turl='" + url + '\''
            + "\n\twebUrl='" + webUrl + '\''
            + "\n\tnumber=" + number
            + "\n\tstate='" + state + '\''
            + "\n\tblocked=" + blocked
            + "\n\tmessage='" + message + '\''
            + "\n\tcommit='" + commit + '\''
            + "\n\tbranch='" + branch + '\''
            + "\n\tsource='" + source + '\''
            + "\n\tcreatedAt=" + createdAt
            + "\n\tscheduledAt=" + scheduledAt
            + "\n\tstartedAt=" + startedAt
            + "\n\tfinishedAt=" + finishedAt
            + "\n\tauthor=" + author
            + "\n\tcreator=" + creator
            + "\n\tpipeline=" + pipeline
            + "\n\tjobs=" + jobs
            + "\n\trebuiltFrom=" + rebuiltFrom
            + "\n\tpullRequest=" + pullRequest
            + "\n}";
    }

    // TODO
    // env
    // metadata
    // pull_request
}
