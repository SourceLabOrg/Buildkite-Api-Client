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

public class AnnotationFilters {
    private final String orgIdSlug;
    private final String pipelineIdSlug;
    private final long buildNumber;

    private final PageOptions pageOptions;

    /**
     * Builder for {@link AnnotationFilters}.
     * @return Builder for {@link AnnotationFilters}.
     */
    public static AnnotationFiltersBuilder newBuilder() {
        return new AnnotationFiltersBuilder();
    }

    /**
     * Constructor.
     */
    public AnnotationFilters(final String orgIdSlug, final String pipelineIdSlug, final long buildNumber, final PageOptions pageOptions) {
        this.orgIdSlug = orgIdSlug;
        this.pipelineIdSlug = pipelineIdSlug;
        this.buildNumber = buildNumber;
        this.pageOptions = pageOptions == null ? PageOptions.getDefault() : pageOptions;
    }

    public String getOrgIdSlug() {
        return orgIdSlug;
    }

    public String getPipelineIdSlug() {
        return pipelineIdSlug;
    }

    public long getBuildNumber() {
        return buildNumber;
    }

    public PageOptions getPageOptions() {
        return pageOptions;
    }

    @Override
    public String toString() {
        return "AnnotationFilters{"
            + "\n\torgIdSlug='" + orgIdSlug + '\''
            + "\n\tpipelineIdSlug='" + pipelineIdSlug + '\''
            + "\n\tbuildNumber=" + buildNumber
            + "\n\tpageOptions=" + pageOptions
            + "\n}";
    }
}
