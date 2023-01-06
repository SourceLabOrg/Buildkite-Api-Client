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

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Optional Properties to filter Builds.
 */
public class BuildFilters {
    private final Set<String> branches;
    private final Set<String> commits;
    private final ZonedDateTime createdFrom;
    private final ZonedDateTime createdTo;
    private final String creator;
    private final ZonedDateTime finishedFrom;
    private final Boolean includeRetriedJobs;
    private final Map<String, String> metaData;
    private final Set<String> states;

    private final PageOptions pageOptions;

    /**
     * Builder for {@link BuildFilters}.
     * @return Builder for {@link BuildFilters}.
     */
    public static BuildFiltersBuilder newBuilder() {
        return new BuildFiltersBuilder();
    }

    /**
     * Constructor.
     * Use {@link BuildFiltersBuilder} to create instances.
     */
    public BuildFilters(
        final Set<String> branches,
        final Set<String> commits,
        final ZonedDateTime createdFrom,
        final ZonedDateTime createdTo,
        final String creator,
        final ZonedDateTime finishedFrom,
        final Boolean includeRetriedJobs,
        final Map<String, String> metaData,
        final Set<String> states,
        final PageOptions pageOptions
    ) {
        this.branches = Collections.unmodifiableSet(new HashSet<>(branches));
        this.commits = Collections.unmodifiableSet(new HashSet<>(commits));
        this.createdFrom = createdFrom;
        this.createdTo = createdTo;
        this.creator = creator;
        this.finishedFrom = finishedFrom;
        this.includeRetriedJobs = includeRetriedJobs;
        this.metaData = Collections.unmodifiableMap(new HashMap<>(metaData));
        this.states = Collections.unmodifiableSet(new HashSet<>(states));
        this.pageOptions = pageOptions;
    }

    public Set<String> getBranches() {
        return branches;
    }

    public Set<String> getCommits() {
        return commits;
    }

    public ZonedDateTime getCreatedFrom() {
        return createdFrom;
    }

    public ZonedDateTime getCreatedTo() {
        return createdTo;
    }

    public String getCreator() {
        return creator;
    }

    public ZonedDateTime getFinishedFrom() {
        return finishedFrom;
    }

    public Boolean getIncludeRetriedJobs() {
        return includeRetriedJobs;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Set<String> getStates() {
        return states;
    }

    public PageOptions getPageOptions() {
        return pageOptions;
    }

    @Override
    public String toString() {
        return "BuildRequestOptions{"
            + "branches=" + branches
            + ", commits=" + commits
            + ", createdFrom=" + createdFrom
            + ", createdTo=" + createdTo
            + ", creator='" + creator + '\''
            + ", finishedFrom=" + finishedFrom
            + ", includeRetriedJobs=" + includeRetriedJobs
            + ", metaData=" + metaData
            + ", state=" + states
            + ", pageOptions=" + pageOptions
            + '}';
    }
}
