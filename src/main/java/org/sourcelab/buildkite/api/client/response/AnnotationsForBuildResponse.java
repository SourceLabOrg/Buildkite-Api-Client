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

import org.sourcelab.buildkite.api.client.request.GetAnnotationsForBuildRequest;
import org.sourcelab.buildkite.api.client.request.PageableRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the results from the /v2/organizations/{org.slug}/pipelines/{pipeline.slug}/builds/{build.number}/annotations API end point.
 */
public class AnnotationsForBuildResponse implements PageableResponse<AnnotationsForBuildResponse> {
    private final PagingLinks pagingLinks;
    private final List<Annotation> annotations;
    private final GetAnnotationsForBuildRequest originalRequest;

    /**
     * Constructor.
     * @param pagingLinks Paging links for results.
     * @param annotations All annotations returned in the response.
     * @param originalRequest The original request used to retrieve these results.
     */
    public AnnotationsForBuildResponse(
        final PagingLinks pagingLinks,
        final List<Annotation> annotations,
        final GetAnnotationsForBuildRequest originalRequest
    ) {
        this.pagingLinks = Objects.requireNonNull(pagingLinks);
        this.annotations = Collections.unmodifiableList(new ArrayList<>(annotations));
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
    public PageableRequest<AnnotationsForBuildResponse> getOriginalRequest() {
        return originalRequest;
    }

    /**
     * All of the annotations returned from the API response.
     * @return All of the annotations returned from the API response for the current page.
     */
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * The total number of builds found.
     * @return The total number of builds found.
     */
    public int count() {
        return getAnnotations().size();
    }

    /**
     * Get all build Ids.
     * @return All build ids.
     */
    public List<String> getAnnotationIds() {
        return getAnnotations().stream()
            .map(Annotation::getId)
            .collect(Collectors.toList());
    }

    /**
     * Given an annotation Id, return the annotation associated with the Id.
     * @param annotationId Id of the annotation to retrieve.
     * @return Build by buildId.
     */
    public Optional<Annotation> getAnnotationById(final String annotationId) {
        Objects.requireNonNull(annotationId);
        return getAnnotations().stream()
            .filter((annotation) -> annotation.getId().equals(annotationId))
            .findFirst();
    }

    @Override
    public String toString() {
        return "AnnotationsForBuildResponse{"
            + "\n\tpagingLinks=" + pagingLinks
            + "\n\tannotations=" + annotations
            + "\n\toriginalRequest=" + originalRequest
            + "\n}";
    }
}
