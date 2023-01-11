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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.sourcelab.buildkite.api.client.response.Author;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBuildOptions {
    @JsonIgnore
    private final String organizationIdSlug;
    @JsonIgnore
    private final String pipelineIdSlug;

    private final String commit;
    private final String branch;
    private final String message;

    private final Author author;

    private final Map<String, String> env;
    private final Map<String, String> meta;

    private final String cleanCheckout;
    private final String ignorePipelineBranchFilters;
    private final String pullRequestBaseBranch;
    private final String pullRequestId;
    private final String pullRequestRepository;

    /**
     * Create new builder for creating {@link CreateBuildOptions} instances.
     * @return new builder instance.
     */
    public static CreateBuildOptionsBuilder newBuilder() {
        return new CreateBuildOptionsBuilder();
    }

    /**
     * Constructor.
     * Use {@link CreateBuildOptions#newBuilder()}}.
     */
    public CreateBuildOptions(
        final String organizationIdSlug,
        final String pipelineIdSlug,
        final String commitId,
        final String branch,
        final String message,
        final Author author,
        final Map<String, String> env,
        final Map<String, String> meta,
        final Boolean cleanCheckout,
        final Boolean ignorePipelineBranchFilters,
        final String pullRequestBaseBranch,
        final String pullRequestId,
        final String pullRequestRepository
    ) {
        this.organizationIdSlug = organizationIdSlug;
        this.pipelineIdSlug = pipelineIdSlug;
        this.commit = commitId;
        this.branch = branch;
        this.message = message;

        this.author = author;

        final Map<String, String> envMap = new HashMap<>();
        if (env != null) {
            envMap.putAll(env);
        }
        this.env = Collections.unmodifiableMap(envMap);

        final Map<String, String> metaMap = new HashMap<>();
        if (meta != null) {
            metaMap.putAll(meta);
        }
        this.meta = Collections.unmodifiableMap(metaMap);

        if (ignorePipelineBranchFilters != null) {
            if (ignorePipelineBranchFilters) {
                this.ignorePipelineBranchFilters = "true";
            } else {
                this.ignorePipelineBranchFilters = "false";
            }
        } else {
            this.ignorePipelineBranchFilters = null;
        }

        if (cleanCheckout != null) {
            if (cleanCheckout) {
                this.cleanCheckout = "true";
            } else {
                this.cleanCheckout = "false";
            }
        } else {
            this.cleanCheckout = null;
        }

        this.pullRequestBaseBranch = pullRequestBaseBranch;
        this.pullRequestId = pullRequestId;
        this.pullRequestRepository = pullRequestRepository;
    }

    public String getOrganizationIdSlug() {
        return organizationIdSlug;
    }

    public String getPipelineIdSlug() {
        return pipelineIdSlug;
    }

    @JsonGetter("commit")
    public String getCommit() {
        return commit;
    }

    @JsonGetter("branch")
    public String getBranch() {
        return branch;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    @JsonGetter("author")
    public Author getAuthor() {
        return author;
    }

    @JsonGetter("env")
    public Map<String, String> getEnv() {
        return env;
    }

    @JsonGetter("meta")
    public Map<String, String> getMeta() {
        return meta;
    }

    @JsonGetter("clean_checkout")
    public String getCleanCheckout() {
        return cleanCheckout;
    }

    @JsonGetter("ignore_pipeline_branch_filters")
    public String getIgnorePipelineBranchFilters() {
        return ignorePipelineBranchFilters;
    }

    @JsonGetter("pull_request_base_branch")
    public String getPullRequestBaseBranch() {
        return pullRequestBaseBranch;
    }

    @JsonGetter("pull_request_id")
    public String getPullRequestId() {
        return pullRequestId;
    }

    @JsonGetter("pull_request_repository")
    public String getPullRequestRepository() {
        return pullRequestRepository;
    }

    @Override
    public String toString() {
        return "CreateBuildOptions{"
            + "organizationIdSlug='" + organizationIdSlug + '\''
            + ", pipelineIdSlug='" + pipelineIdSlug + '\''
            + ", commitId='" + commit + '\''
            + ", branch='" + branch + '\''
            + ", message='" + message + '\''
            + ", author=" + author
            + ", env=" + env
            + ", meta=" + meta
            + '}';
    }
}
