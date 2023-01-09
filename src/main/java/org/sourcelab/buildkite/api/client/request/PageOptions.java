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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Paging options.
 */
public class PageOptions {
    private final int page;
    private final int perPage;

    private static final PageOptions DEFAULT = new PageOptions(1, 30);

    /**
     * Default instance with sane default properties.
     * @return Default instance with sane default properties.
     */
    public static PageOptions getDefault() {
        return DEFAULT;
    }

    /**
     * Constructor.
     * @param page Which page to request.
     * @param perPage How many entries per page.
     */
    public PageOptions(int page, int perPage) {
        if (page < 0) {
            page = 1;
        }
        this.page = page;

        if (perPage < 0) {
            perPage = 1;
        } else if (perPage > 100) {
            perPage = 100;
        }
        this.perPage = perPage;
    }

    /**
     * Parses the given url and creates a PageOptions instance from the parameters.
     *
     * @param url The url to parse for per_page and page parameters.
     * @return PageOptions instance populated from the supplied url.
     * @throws IllegalArgumentException if passed an invalid url.
     */
    public static PageOptions fromUrl(final String url) {
        Objects.requireNonNull(url);

        final Pattern perPagePattern = Pattern.compile(".*[?&]per_page=([0-9]+).*");
        final Pattern pagePattern = Pattern.compile(".*[?&]page=([0-9]+).*");

        Integer perPage = null;
        Integer page = null;

        final Matcher perPageMatcher = perPagePattern.matcher(url);
        if (perPageMatcher.matches() && perPageMatcher.groupCount() == 1) {
            perPage = Integer.parseInt(perPageMatcher.group(1));
        }

        final Matcher pageMatcher = pagePattern.matcher(url);
        if (pageMatcher.matches() && pageMatcher.groupCount() == 1) {
            page = Integer.parseInt(pageMatcher.group(1));
        }

        if (perPage == null || page == null) {
            throw new IllegalArgumentException("Unable to parse url " + url);
        }

        return new PageOptions(page, perPage);
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    @Override
    public String toString() {
        return "PageOptions{"
            + "page=" + page
            + ", perPage=" + perPage
            + '}';
    }
}
