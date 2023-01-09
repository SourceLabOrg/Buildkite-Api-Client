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

import org.sourcelab.buildkite.api.client.request.PageOptions;

/**
 * Represents the 'Link' header for paging results.
 */
public class PagingLinks {
    private String prevUrl;
    private String nextUrl;
    private String firstUrl;
    private String lastUrl;

    /**
     * Create new Builder instance for PagingLinks.
     * @return New Builder instance for PagingLinks.
     */
    public static PagingLinksBuilder newBuilder() {
        return new PagingLinksBuilder();
    }

    /**
     * Constructor.
     */
    public PagingLinks(final String prevUrl, final String nextUrl, final String firstUrl, final String lastUrl) {
        this.prevUrl = prevUrl;
        this.nextUrl = nextUrl;
        this.firstUrl = firstUrl;
        this.lastUrl = lastUrl;
    }

    public boolean hasFirstUrl() {
        return firstUrl != null;
    }

    public boolean hasLastUrl() {
        return lastUrl != null;
    }

    public boolean hasNextUrl() {
        return nextUrl != null;
    }

    public boolean hasPrevUrl() {
        return prevUrl != null;
    }

    /**
     * Get the Previous Url.
     * @return Previous Url.
     * @throws IllegalStateException If no previous url is defined.
     */
    public String getPrevUrl() {
        if (!hasPrevUrl()) {
            throw new IllegalStateException("Previous Url is not defined.");
        }
        return prevUrl;
    }

    /**
     * Get the Next Url.
     * @return Next Url.
     * @throws IllegalStateException If no next url is defined.
     */
    public String getNextUrl() {
        if (!hasNextUrl()) {
            throw new IllegalStateException("Next Url is not defined.");
        }
        return nextUrl;
    }

    /**
     * Get the First Url.
     * @return First Url.
     * @throws IllegalStateException If no first url is defined.
     */
    public String getFirstUrl() {
        if (!hasFirstUrl()) {
            throw new IllegalStateException("First Url is not defined.");
        }
        return firstUrl;
    }

    /**
     * Get the Last Url.
     * @return Last Url.
     * @throws IllegalStateException If no last url is defined.
     */
    public String getLastUrl() {
        if (!hasLastUrl()) {
            throw new IllegalStateException("Last Url is not defined.");
        }
        return lastUrl;
    }

    /**
     * If the Last Page URL is populated, calculate the total number of entries.
     * @return If the Last Page URL is populated, calculate the total number of entries.
     * @throws IllegalStateException If the Last Url is not defined.
     */
    public long getTotalNumberOfEntries() {
        if (!hasLastUrl()) {
            throw new IllegalStateException("Last Url is not defined, cannot determine total number of entries.");
        }
        final PageOptions pageOptions = PageOptions.fromUrl(getLastUrl());
        return ((long) pageOptions.getPage()) * ((long) pageOptions.getPerPage());
    }

    @Override
    public String toString() {
        return "LinkHeader{"
                + "prevUrl='" + prevUrl + '\''
                + ", nextUrl='" + nextUrl + '\''
                + ", firstUrl='" + firstUrl + '\''
                + ", lastUrl='" + lastUrl + '\''
                + '}';
    }
}
