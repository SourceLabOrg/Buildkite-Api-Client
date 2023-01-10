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

/**
 * Builder for {@link BuildFilters}.
 */
public final class PipelineFiltersBuilder {
    private PageOptions pageOptions = null;
    private String orgIdSlug = null;
    private String pipelineIdSlug;

    /**
     * Constructor.
     */
    public PipelineFiltersBuilder() {
    }

    /**
     * Set the number of results per page.
     * @param perPage Set the number of results per page.
     * @return BuildOrganizationFiltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withPerPage(final int perPage) {
        if (this.pageOptions == null) {
            withPageOptions(PageOptions.getDefault());
        }
        return withPageOptions(new PageOptions(pageOptions.getPage(), perPage));
    }

    /**
     * Set the page to retrieve.
     * @param page Set the page to retrieve.
     * @return BuildOrganizationFiltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withPage(final int page) {
        if (this.pageOptions == null) {
            withPageOptions(PageOptions.getDefault());
        }
        return withPageOptions(new PageOptions(page, pageOptions.getPerPage()));
    }

    /**
     * Apply Paging Options.
     * @param page Set the page to retrieve.
     * @param perPage Set the number of results per page.
     * @return BuildOrganizationFiltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withPageOptions(final int page, final int perPage) {
        return withPageOptions(new PageOptions(page, perPage));
    }

    /**
     * Apply Paging Options.
     * @param pageOptions Paging options to apply.
     * @return BuildOrganizationFiltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withPageOptions(final PageOptions pageOptions) {
        this.pageOptions = pageOptions;
        return this;
    }

    /**
     * Filter by the given Organization.
     * @param orgIdSlug Organization to filter by.
     * @return PipelineFIltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withOrganization(final String orgIdSlug) {
        this.orgIdSlug = orgIdSlug;
        return this;
    }

    /**
     * Filter by the given Organization and Pipeline.
     * @param orgIdSlug Organization to filter by.
     * @param pipelineIdSlug Pipeline to filter by.
     * @return PipelineFIltersBuilder for method chaining.
     */
    public PipelineFiltersBuilder withPipeline(final String orgIdSlug, final String pipelineIdSlug) {
        withOrganization(orgIdSlug);
        this.pipelineIdSlug = pipelineIdSlug;
        return this;
    }

    /**
     * New OrganizationFilters instance using configured properties.
     * @return New OrganizationFilters instance using configured properties.
     */
    public PipelineFilters build() {
        // Validation
        if (pipelineIdSlug != null) {
            if (orgIdSlug == null) {
                throw new IllegalStateException("If Pipeline is provided, then Organization must be provided.");
            }
        }
        if (orgIdSlug == null) {
            throw new IllegalStateException("Organization must be provided.");
        }

        return new PipelineFilters(
            pageOptions,
            orgIdSlug,
            pipelineIdSlug
        );
    }
}
