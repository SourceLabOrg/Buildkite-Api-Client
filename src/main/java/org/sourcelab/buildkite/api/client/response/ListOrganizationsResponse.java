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

import org.sourcelab.buildkite.api.client.request.ListOrganizationsRequest;
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
public class ListOrganizationsResponse implements PageableResponse<ListOrganizationsResponse> {
    private final PagingLinks pagingLinks;
    private final List<Organization> organizations;
    private final ListOrganizationsRequest originalRequest;

    /**
     * Constructor.
     * @param pagingLinks Paging links for results.
     * @param organizations All organizations returned in the response.
     * @param originalRequest The original request used to retrieve these results.
     */
    public ListOrganizationsResponse(
            final PagingLinks pagingLinks,
            final List<Organization> organizations,
            final ListOrganizationsRequest originalRequest
    ) {
        this.pagingLinks = Objects.requireNonNull(pagingLinks);
        this.organizations = Collections.unmodifiableList(new ArrayList<>(organizations));
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
    public PageableRequest<ListOrganizationsResponse> getOriginalRequest() {
        return originalRequest;
    }

    /**
     * All of the Organizations returned from the API response.
     * @return All of the Organizations returned from the API response.
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     * The total number of builds found.
     * @return The total number of builds found.
     */
    public int count() {
        return getOrganizations().size();
    }

    /**
     * Get all organization Ids.
     * @return All organization ids.
     */
    public List<String> getOrganizationIds() {
        return getOrganizations().stream()
            .map(Organization::getId)
            .collect(Collectors.toList());
    }

    /**
     * Get all organization names.
     * @return All organization names.
     */
    public List<String> getOrganizationNames() {
        return getOrganizations().stream()
            .map(Organization::getName)
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Get all organization slugs.
     * @return All organization slugs.
     */
    public List<String> getOrganizationSlugIds() {
        return getOrganizations().stream()
                .map(Organization::getSlug)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Given an organization Id, return the organization associated with the Id.
     * @param organizationId Id of the organization to retrieve.
     * @return Organization by organizationId.
     */
    public Optional<Organization> getOrganizationById(final String organizationId) {
        Objects.requireNonNull(organizationId);
        return getOrganizations().stream()
            .filter((organization) -> organizationId.equals(organization.getId()))
            .findFirst();
    }

    /**
     * Given an organization name, return the organization associated with the Name.
     * @param organizationName Name of the organization to retrieve.
     * @return Organization by organization name.
     */
    public Optional<Organization> getOrganizationByName(final String organizationName) {
        Objects.requireNonNull(organizationName);
        return getOrganizations().stream()
            .filter((organization) -> organizationName.equals(organization.getName()))
            .findFirst();
    }

    /**
     * Given an organization slug, return the organization associated with the slug.
     * @param slug slug of the organization to retrieve.
     * @return Organization by organization slug.
     */
    public Optional<Organization> getOrganizationBySlug(final String slug) {
        Objects.requireNonNull(slug);
        return getOrganizations().stream()
                .filter((organization) -> slug.equals(organization.getSlug()))
                .findFirst();
    }

    @Override
    public String toString() {
        return "ListOrganizationsResponse{"
            + "pagingLinks=" + pagingLinks
            + ", organizations=" + organizations
            + ", originalRequest=" + originalRequest
            + '}';
    }
}
