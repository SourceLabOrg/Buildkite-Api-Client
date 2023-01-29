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

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Builder for {@link BuildFilters}.
 */
public final class BuildFiltersBuilder {
    private Set<String> branches = new HashSet<>();
    private Set<String> commits = new HashSet<>();
    private ZonedDateTime createdFrom = null;
    private ZonedDateTime createdTo = null;
    private String creator = null;
    private ZonedDateTime finishedFrom = null;
    private Boolean includeRetriedJobs = null;
    private Map<String, String> metaData = new HashMap<>();
    private Set<String> states = new HashSet<>();

    private PageOptions pageOptions = null;

    private String orgIdSlug = null;

    /**
     * If pipelineSlugId is provided, then orgIdSlug MUST be provided.
     */
    private String pipelineIdSlug = null;

    /**
     * Constructor.
     */
    public BuildFiltersBuilder() {
    }

    /**
     * Apply filter over one or more branches.
     * @param branch Branch names to filter over.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withBranch(final String branch) {
        this.branches.add(branch);
        return this;
    }

    /**
     * Apply filter over one or more branches.
     * @param branches One or more branch names to filter by.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withBranches(final String ... branches) {
        this.branches.addAll(Arrays.asList(branches));
        return this;
    }

    /**
     * Apply filter over one or more branches.
     * @param branches One or more branch names to filter by.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withBranches(final Collection<String> branches) {
        Objects.requireNonNull(branches);
        this.branches.addAll(branches);
        return this;
    }

    /**
     * Apply filter over one or more commits.
     * @param commit Commit hashes to filter by.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCommit(final String commit) {
        this.commits.add(commit);
        return this;
    }

    /**
     * Apply filter over one or more commits.
     * @param commits One or more commit hashes to filter by.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCommits(final String ... commits) {
        this.commits.addAll(Arrays.asList(commits));
        return this;
    }

    /**
     * Apply filter over one or more commits.
     * @param commits One or more commit hashes to filter by.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCommits(final Collection<String> commits) {
        Objects.requireNonNull(commits);
        this.commits.addAll(commits);
        return this;
    }

    /**
     * Apply filter over results by builds created on or after the given time.
     * @param createdFrom Filters the results by builds created on or after the given time.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCreatedFrom(final ZonedDateTime createdFrom) {
        this.createdFrom = createdFrom;
        return this;
    }

    /**
     * Filters the results by builds created before the given time.
     * @param createdTo Filters the results by builds created before the given time.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCreatedTo(final ZonedDateTime createdTo) {
        this.createdTo = createdTo;
        return this;
    }

    /**
     * Filters the results by the user who created the build.  Supply userId/UUID.
     * @param creatorUuid Filters the results by the user who created the build.  Supply userId/UUID.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withCreator(final String creatorUuid) {
        this.creator = creatorUuid;
        return this;
    }

    /**
     * Filters the results by builds finished on or after the given time.
     * @param finishedFrom Filters the results by builds finished on or after the given time.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withFinishedFrom(final ZonedDateTime finishedFrom) {
        this.finishedFrom = finishedFrom;
        return this;
    }

    /**
     * Setting to TRUE, include all retried job executions in each build's jobs list.
     * Setting to FALSE , you'll see only the most recently run job for each step.
     * @param includeRetriedJobs Setting to TRUE, include all retried job executions in each build's jobs list.
     *                           Setting to FALSE , you'll see only the most recently run job for each step.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withIncludeRetriedJobs(final Boolean includeRetriedJobs) {
        if (includeRetriedJobs == false) {
            this.includeRetriedJobs = null;
        } else {
            this.includeRetriedJobs = includeRetriedJobs;
        }
        return this;
    }

    /**
     * Filters the results by the given meta-data.
     * @param name Metadata key name.
     * @param value Metadata value.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withMetaData(final String name, final Object value) {
        Objects.requireNonNull(name);
        if (value == null) {
            metaData.remove(name);
        } else {
            this.metaData.put(name, value.toString());
        }
        return this;
    }

    /**
     * Filters the results by the given meta-data.
     * @param metaData Metadata values.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withMetaData(final Map<String, String> metaData) {
        Objects.requireNonNull(metaData);
        this.metaData.putAll(metaData);
        return this;
    }

    /**
     * Utility helper to fluently select states from known valid states.
     * @return Utility helper to fluently select states from known valid states.
     */
    public StateChooser withStateChooser() {
        return new StateChooser(this);
    }

    /**
     * Filters the results by the given build state.
     * The finished state is a shortcut to automatically search for builds with passed, failed, blocked, canceled states.
     *
     * See also {@link BuildFiltersBuilder#withStateChooser()}.
     *
     * Valid states: running, scheduled, passed, failing, failed, blocked, canceled, canceling, skipped, not_run, finished
     * @param state Filters the results by the given build state.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withState(final String state) {
        this.states.add(state);
        return this;
    }

    /**
     * Filters the results by the given build state.
     * The finished state is a shortcut to automatically search for builds with passed, failed, blocked, canceled states.
     *
     * Valid states: running, scheduled, passed, failing, failed, blocked, canceled, canceling, skipped, not_run, finished
     * @param states Filters the results by the given build state.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withStates(final String ... states) {
        this.states.addAll(Arrays.asList(states));
        return this;
    }

    /**
     * Filters the results by the given build state.
     * The finished state is a shortcut to automatically search for builds with passed, failed, blocked, canceled states.
     *
     * Valid states: running, scheduled, passed, failing, failed, blocked, canceled, canceling, skipped, not_run, finished
     * @param states Filters the results by the given build state.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withStates(final Collection<String> states) {
        Objects.requireNonNull(states);
        this.states.addAll(states);
        return this;
    }

    /**
     * Set the number of results per page.
     * @param perPage Set the number of results per page.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withPerPage(final int perPage) {
        if (this.pageOptions == null) {
            withPageOptions(PageOptions.getDefault());
        }
        return withPageOptions(new PageOptions(pageOptions.getPage(), perPage));
    }

    /**
     * Set the page to retrieve.
     * @param page Set the page to retrieve.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withPage(final int page) {
        if (this.pageOptions == null) {
            withPageOptions(PageOptions.getDefault());
        }
        return withPageOptions(new PageOptions(page, pageOptions.getPerPage()));
    }

    /**
     * Apply Paging Options.
     * @param page Set the page to retrieve.
     * @param perPage Set the number of results per page.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withPageOptions(final int page, final int perPage) {
        return withPageOptions(new PageOptions(page, perPage));
    }

    /**
     * Apply Paging Options.
     * @param pageOptions Paging options to apply.
     * @return BuildFiltersBuilder for method chaining.
     */
    public BuildFiltersBuilder withPageOptions(final PageOptions pageOptions) {
        this.pageOptions = pageOptions;
        return this;
    }

    public BuildFiltersBuilder withOrganization(final String orgIdSlug) {
        this.orgIdSlug = orgIdSlug;
        return this;
    }

    public BuildFiltersBuilder withPipeline(final String orgIdSlug, final String pipelineIdSlug) {
        this.pipelineIdSlug = pipelineIdSlug;
        return withOrganization(orgIdSlug);
    }

    /**
     * New BuildFilters instance using configured properties.
     * @return New BuildFilters instance using configured properties.
     * @throws BuilderValidationException if not valid or complete.
     */
    public BuildFilters build() {
        // Validation
        if (pipelineIdSlug != null) {
            if (orgIdSlug == null) {
                throw new BuilderValidationException("If Pipeline is provided, then Organization must be provided.");
            }
        }

        return new BuildFilters(
            branches,
            commits,
            createdFrom,
            createdTo,
            creator,
            finishedFrom,
            includeRetriedJobs,
            metaData,
            states,
            pageOptions,
            orgIdSlug,
            pipelineIdSlug
        );
    }
}
