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

public final class AnnotationFiltersBuilder {
    private String orgIdSlug = null;
    private String pipelineIdSlug = null;
    private Long buildNumber = null;

    private PageOptions pageOptions = null;

    public AnnotationFiltersBuilder withOrgIdSlug(final String orgIdSlug) {
        this.orgIdSlug = orgIdSlug;
        return this;
    }

    public AnnotationFiltersBuilder withPipelineIdSlug(final String pipelineIdSlug) {
        this.pipelineIdSlug = pipelineIdSlug;
        return this;
    }

    public AnnotationFiltersBuilder withBuildNumber(final long buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    /**
     * Set the number of results per page.
     * @param perPage Set the number of results per page.
     * @return BuildFiltersBuilder for method chaining.
     */
    public AnnotationFiltersBuilder withPerPage(final int perPage) {
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
    public AnnotationFiltersBuilder withPage(final int page) {
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
    public AnnotationFiltersBuilder withPageOptions(final int page, final int perPage) {
        return withPageOptions(new PageOptions(page, perPage));
    }

    /**
     * Apply Paging Options.
     * @param pageOptions Paging options to apply.
     * @return BuildFiltersBuilder for method chaining.
     */
    public AnnotationFiltersBuilder withPageOptions(final PageOptions pageOptions) {
        this.pageOptions = pageOptions;
        return this;
    }

    /**
     * Create new AnnotationFilters instance from the builder.
     * @return new AnnotationFilters instance from the builder.
     */
    public AnnotationFilters build() {
        // Validate
        if (pipelineIdSlug == null) {
            throw new BuilderValidationException("Pipeline must be provided.");
        }
        if (orgIdSlug == null) {
            throw new BuilderValidationException("Organization must be provided.");
        }
        if (buildNumber == null) {
            throw new BuilderValidationException("Build Number must be provided.");
        }
        return new AnnotationFilters(orgIdSlug, pipelineIdSlug, buildNumber, pageOptions);
    }
}
