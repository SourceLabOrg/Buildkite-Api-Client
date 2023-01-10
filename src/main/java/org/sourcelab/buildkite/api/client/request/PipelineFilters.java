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
 * Optional Properties to filter Organizations.
 */
public class PipelineFilters implements Filters {
    private final PageOptions pageOptions;
    private final String orgIdSlug;
    private final String pipelineIdSlug;

    /**
     * Builder for {@link PipelineFilters}.
     * @return Builder for {@link PipelineFilters}.
     */
    public static PipelineFiltersBuilder newBuilder() {
        return new PipelineFiltersBuilder();
    }

    /**
     * Constructor.
     * Use {@link BuildFiltersBuilder} to create instances.
     */
    public PipelineFilters(
        final PageOptions pageOptions,
        final String orgIdSlug,
        final String pipelineIdSlug
    ) {
        this.pageOptions = pageOptions == null ? PageOptions.getDefault() : pageOptions;
        this.orgIdSlug = orgIdSlug;
        this.pipelineIdSlug = pipelineIdSlug;
    }

    public String getOrgIdSlug() {
        return orgIdSlug;
    }

    public boolean hasOrgIdSlug() {
        return orgIdSlug != null;
    }

    public String getPipelineIdSlug() {
        return pipelineIdSlug;
    }

    public boolean hasPipelineIdSlug() {
        return pipelineIdSlug != null;
    }

    public PageOptions getPageOptions() {
        return pageOptions;
    }

    @Override
    public String toString() {
        return "PipelineFilters{"
                + "pageOptions=" + pageOptions
                + ", orgIdSlug='" + orgIdSlug + '\''
                + ", pipelineIdSlug='" + pipelineIdSlug + '\''
                + '}';
    }
}
