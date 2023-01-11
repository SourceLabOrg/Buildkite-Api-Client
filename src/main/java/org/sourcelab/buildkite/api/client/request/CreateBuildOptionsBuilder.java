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
import org.sourcelab.buildkite.api.client.response.Author;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Used to create {@link CreateBuildOptions} instances.
 */
public final class CreateBuildOptionsBuilder {
    // Required fields
    private String organizationIdSlug = null;
    private String pipelineIdSlug = null;
    private String commit = null;
    private String branch = null;

    // Optional Fields
    private String message = null;
    private Author author = null;

    private Map<String, String> env = new HashMap<>();
    private Map<String, String> meta = new HashMap<>();

    private Boolean cleanCheckout = null;
    private Boolean ignorePipelineBranchFilters = null;
    private String pullRequestBaseBranch = null;
    private String pullRequestId = null;
    private String pullRequestRepository = null;

    public CreateBuildOptionsBuilder withOrganization(final String organizationSlug) {
        this.organizationIdSlug = organizationSlug;
        return this;
    }

    public CreateBuildOptionsBuilder withPipeline(final String pipelineSlug) {
        this.pipelineIdSlug = pipelineSlug;
        return this;
    }

    /**
     * Set the organization and pipeline slugs.
     *
     * @param organizationSlug organization slug.
     * @param pipelineSlug pipeline slug.
     * @return CreateBuildOptionsBuilder for method chaining.
     */
    public CreateBuildOptionsBuilder withPipeline(final String organizationSlug, final String pipelineSlug) {
        this.pipelineIdSlug = pipelineSlug;
        this.organizationIdSlug = organizationSlug;
        return this;
    }

    public CreateBuildOptionsBuilder withCommit(final String commit) {
        this.commit = commit;
        return this;
    }

    public CreateBuildOptionsBuilder withBranch(final String branch) {
        this.branch = branch;
        return this;
    }

    public CreateBuildOptionsBuilder withMessage(final String message) {
        this.message = message;
        return this;
    }

    public CreateBuildOptionsBuilder withAuthor(final Author author) {
        this.author = author;
        return this;
    }

    public CreateBuildOptionsBuilder withAuthor(final String authorName, final String authorEmail) {
        return withAuthor(new Author("", authorName, authorEmail));
    }

    public CreateBuildOptionsBuilder withEnv(final Map<String, String> env) {
        this.env.putAll(Objects.requireNonNull(env));
        return this;
    }

    public CreateBuildOptionsBuilder withEnv(final String key, final String value) {
        this.env.put(key, value);
        return this;
    }

    public CreateBuildOptionsBuilder withMeta(final Map<String, String> meta) {
        this.meta.putAll(Objects.requireNonNull(meta));
        return this;
    }

    public CreateBuildOptionsBuilder withMeta(final String key, final String value) {
        this.meta.put(key, value);
        return this;
    }

    public CreateBuildOptionsBuilder withCleanCheckout(final boolean useCleanCheckout) {
        this.cleanCheckout = useCleanCheckout;
        return this;
    }

    public CreateBuildOptionsBuilder withIgnorePipelineBranchFilters(final boolean ignorePipelineBranchFilters) {
        this.ignorePipelineBranchFilters = ignorePipelineBranchFilters;
        return this;
    }

    public CreateBuildOptionsBuilder withPullRequestBaseBranch(final String pullRequestBaseBranch) {
        this.pullRequestBaseBranch = pullRequestBaseBranch;
        return this;
    }

    public CreateBuildOptionsBuilder withPullRequestId(final String pullRequestId) {
        this.pullRequestId = pullRequestId;
        return this;
    }

    public CreateBuildOptionsBuilder withPullRequestId(final long pullRequestId) {
        this.pullRequestId = Long.toString(pullRequestId);
        return this;
    }

    public CreateBuildOptionsBuilder withPullRequestId(final int pullRequestId) {
        this.pullRequestId = Integer.toString(pullRequestId);
        return this;
    }

    public CreateBuildOptionsBuilder withPullRequestRepository(final String pullRequestRepository) {
        this.pullRequestRepository = pullRequestRepository;
        return this;
    }

    /**
     * New {@link CreateBuildOptions} instance created from defined properties.
     * @return New {@link CreateBuildOptions} instance created from defined properties.
     */
    public CreateBuildOptions build() {
        // Validate
        if (organizationIdSlug == null) {
            throw new BuilderValidationException("Organization must be provided.");
        }
        if (pipelineIdSlug == null) {
            throw new BuilderValidationException("Pipeline must be provided.");
        }
        if (commit == null) {
            throw new BuilderValidationException("CommitId must be provided.");
        }
        if (branch == null) {
            throw new BuilderValidationException("Branch must be provided.");
        }

        return new CreateBuildOptions(
            organizationIdSlug,
            pipelineIdSlug,
            commit,
            branch,
            message,
            author,
            env,
            meta,
            cleanCheckout,
            ignorePipelineBranchFilters,
            pullRequestBaseBranch,
            pullRequestId,
            pullRequestRepository
        );
    }
}
