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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListBuildsResponse {
    private final PagingLinks pagingLinks;
    private final List<Build> builds;

    public ListBuildsResponse(final PagingLinks pagingLinks, final List<Build> builds) {
        this.pagingLinks = Objects.requireNonNull(pagingLinks);
        this.builds = Collections.unmodifiableList(new ArrayList<>(builds));
    }

    public PagingLinks getPagingLinks() {
        return pagingLinks;
    }

    public List<Build> getBuilds() {
        return builds;
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
                + '}';
    }
}
