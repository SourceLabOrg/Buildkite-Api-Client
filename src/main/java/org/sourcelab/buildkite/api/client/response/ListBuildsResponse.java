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

import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.PageableRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the results from the /v2/build API end point.
 */
public class ListBuildsResponse implements PageableResponse<ListBuildsResponse> {
    private final PagingLinks pagingLinks;
    private final List<Build> builds;
    private final ListBuildsRequest originalRequest;

    /**
     * Constructor.
     * @param pagingLinks Paging links for results.
     * @param builds All builds returned in the response.
     * @param originalRequest The original request used to retrieve these results.
     */
    public ListBuildsResponse(final PagingLinks pagingLinks, final List<Build> builds, final ListBuildsRequest originalRequest) {
        this.pagingLinks = Objects.requireNonNull(pagingLinks);
        this.builds = Collections.unmodifiableList(new ArrayList<>(builds));
        this.originalRequest = originalRequest;
    }

    /**
     * Paging Link references for the results.
     * @return Paging Link references for the results.
     */
    public PagingLinks getPagingLinks() {
        return pagingLinks;
    }

    @Override
    public PageableRequest<ListBuildsResponse> getOriginalRequest() {
        return originalRequest;
    }

    /**
     * All of the Builds returned from the API response.
     * @return All of the Builds returned from the API response.
     */
    public List<Build> getBuilds() {
        return builds;
    }

    /**
     * The total number of builds found.
     * @return The total number of builds found.
     */
    public int countBuilds() {
        return getBuilds().size();
    }

    /**
     * Get all build Ids.
     * @return All build ids.
     */
    public List<String> getBuildIds() {
        return getBuilds().stream()
            .map(Build::getId)
            .collect(Collectors.toList());
    }

    /**
     * Given a build Id, return the build associated with the Id.
     * @param buildId Id of the build to retrieve.
     * @return Build by buildId.
     */
    public Optional<Build> getBuildById(final String buildId) {
        Objects.requireNonNull(buildId);
        return getBuilds().stream()
            .filter((build) -> build.getId().equals(buildId))
            .findFirst();
    }

    @Override
    public String toString() {
        return "ListBuildsResponse{"
            + "pagingLinks=" + pagingLinks
            + ", builds=" + builds
            + ", originalRequest=" + originalRequest
            + '}';
    }
}
