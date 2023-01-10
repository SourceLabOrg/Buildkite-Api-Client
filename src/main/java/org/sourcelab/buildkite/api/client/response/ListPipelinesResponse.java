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

import org.sourcelab.buildkite.api.client.request.ListPipelinesRequest;
import org.sourcelab.buildkite.api.client.request.PageableRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the results from the v2/organizations/{org.slug}/pipelines API end point.
 */
public class ListPipelinesResponse implements PageableResponse<ListPipelinesResponse> {
    private final PagingLinks pagingLinks;
    private final List<Pipeline> pipelines;
    private final ListPipelinesRequest originalRequest;

    /**
     * Constructor.
     * @param pagingLinks Paging links for results.
     * @param pipelines All pipelines returned in the response.
     * @param originalRequest The original request used to retrieve these results.
     */
    public ListPipelinesResponse(
        final PagingLinks pagingLinks,
        final List<Pipeline> pipelines,
        final ListPipelinesRequest originalRequest
    ) {
        this.pagingLinks = Objects.requireNonNull(pagingLinks);
        this.pipelines = Collections.unmodifiableList(new ArrayList<>(pipelines));
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
    public PageableRequest<ListPipelinesResponse> getOriginalRequest() {
        return originalRequest;
    }

    /**
     * All of the Pipelines returned from the API response.
     * @return All of the Pipelines returned from the API response.
     */
    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    /**
     * The total number of pipelines found.
     * @return The total number of pipelines found.
     */
    public int count() {
        return getPipelines().size();
    }

    /**
     * Get all pipeline Ids.
     * @return All pipeline ids.
     */
    public List<String> getPipelineIds() {
        return getPipelines().stream()
            .map(Pipeline::getId)
            .collect(Collectors.toList());
    }

    /**
     * Get all pipeline names.
     * @return All pipeline names.
     */
    public List<String> getPipelineNames() {
        return getPipelines().stream()
            .map(Pipeline::getName)
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Get all organization slugs.
     * @return All organization slugs.
     */
    public List<String> getPipelineSlugIds() {
        return getPipelines().stream()
            .map(Pipeline::getSlug)
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Given an pipeline Id, return the pipeline associated with the Id.
     * @param pipelineId Id of the pipeline to retrieve.
     * @return pipeline by pipeline id.
     */
    public Optional<Pipeline> getPipelineById(final String pipelineId) {
        Objects.requireNonNull(pipelineId);
        return getPipelines().stream()
            .filter((pipeline) -> pipelineId.equals(pipeline.getId()))
            .findFirst();
    }

    /**
     * Given an pipeline name, return the pipeline associated with the Name.
     * @param pipelineName Name of the pipeline to retrieve.
     * @return pipeline by pipeline name.
     */
    public Optional<Pipeline> getPipelineByName(final String pipelineName) {
        Objects.requireNonNull(pipelineName);
        return getPipelines().stream()
            .filter((pipeline) -> pipelineName.equals(pipeline.getName()))
            .findFirst();
    }

    /**
     * Given an organization slug, return the organization associated with the slug.
     * @param slug slug of the organization to retrieve.
     * @return Organization by organization slug.
     */
    public Optional<Pipeline> getPipelineBySlug(final String slug) {
        Objects.requireNonNull(slug);
        return getPipelines().stream()
                .filter((pipeline) -> slug.equals(pipeline.getSlug()))
                .findFirst();
    }

    @Override
    public String toString() {
        return "ListPipelinesResponse{"
                + "pagingLinks=" + pagingLinks
                + ", pipelines=" + pipelines
                + ", originalRequest=" + originalRequest
                + '}';
    }
}
