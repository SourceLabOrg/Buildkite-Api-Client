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

import org.sourcelab.buildkite.api.client.request.PageableRequest;

/**
 * Interface indicates that a Response from the Buildkite REST Api is Pageable, and can be passed into
 * the following methods to retrieve various pages.
 *   {@link org.sourcelab.buildkite.api.client.BuildkiteClient#nextPage(PageableResponse)} nextPage()}
 *   {@link org.sourcelab.buildkite.api.client.BuildkiteClient#previousPage(PageableResponse)} previousPage()}
 *   {@link org.sourcelab.buildkite.api.client.BuildkiteClient#firstPage(PageableResponse)} firstPage()}
 *   {@link org.sourcelab.buildkite.api.client.BuildkiteClient#lastPage(PageableResponse)} lastPage()}
 *
 * @param <T> The parsed Response object type.
 */
public interface PageableResponse<T> {
    /**
     * Contains references to First, Next, Previous, and Last pages of a Response.
     * @return Contains references to First, Next, Previous, and Last pages of a Response.
     */
    PagingLinks getPagingLinks();

    /**
     * Utility method to determine if there are additional pages available.
     * @return true if there is a next page that can be retrieved, false if not.
     */
    default boolean hasNextPage() {
        return getPagingLinks().hasNextUrl();
    }

    /**
     * Utility method to determine if there are previous pages available.
     * @return true if there is a previous page that can be retrieved, false if not.
     */
    default boolean hasPreviousPage() {
        return getPagingLinks().hasPrevUrl();
    }

    /**
     * Utility method to determine if there is a first page available.
     * @return true if there is a first page that can be retrieved, false if not.
     */
    default boolean hasFirstPage() {
        return getPagingLinks().hasFirstUrl();
    }

    /**
     * Utility method to determine if there is a last page available.
     * @return true if there is a last page that can be retrieved, false if not.
     */
    default boolean hasLastPage() {
        return getPagingLinks().hasLastUrl();
    }

    /**
     * Get the original underlying {@link org.sourcelab.buildkite.api.client.request.Request} used
     * to generate this response.
     *
     * @return Original underlying Request.
     */
    PageableRequest<T> getOriginalRequest();
}
