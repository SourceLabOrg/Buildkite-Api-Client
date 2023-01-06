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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For building/creating new {@link PagingLinks} instances.
 */
public final class PagingLinksBuilder {
    private String prevUrl;
    private String nextUrl;
    private String firstUrl;
    private String lastUrl;

    /**
     * Constructor.
     */
    public PagingLinksBuilder() {
    }

    /**
     * Set the previous Url.
     * @param prevUrl The previous url.
     * @return LinkHeaderBuilder for method chaining,
     */
    public PagingLinksBuilder withPrevUrl(final String prevUrl) {
        this.prevUrl = prevUrl;
        return this;
    }

    /**
     * Set the next Url.
     * @param nextUrl The next url.
     * @return LinkHeaderBuilder for method chaining,
     */
    public PagingLinksBuilder withNextUrl(final String nextUrl) {
        this.nextUrl = nextUrl;
        return this;
    }

    /**
     * Set the first Url.
     * @param firstUrl The first url.
     * @return LinkHeaderBuilder for method chaining,
     */
    public PagingLinksBuilder withFirstUrl(final String firstUrl) {
        this.firstUrl = firstUrl;
        return this;
    }

    /**
     * Set the last Url.
     * @param lastUrl The last url.
     * @return LinkHeaderBuilder for method chaining,
     */
    public PagingLinksBuilder withLastUrl(final String lastUrl) {
        this.lastUrl = lastUrl;
        return this;
    }

    /**
     * Load builder parsing from the Header line.
     * @param headerLine The header line to parse.
     * @return LinkHeaderBuilder for method chaining.
     */
    public PagingLinksBuilder fromHeaderLine(final String headerLine) {
        Objects.requireNonNull(headerLine);

        final Pattern pattern = Pattern.compile("<(.+)>; rel=\"(.+)\"");
        final String[] pieces = headerLine.split(",");
        for (final String piece : pieces) {
            final Matcher matcher = pattern.matcher(piece.trim());
            if (matcher.matches() && matcher.groupCount() == 2) {
                final String url = matcher.group(1).trim();
                final String part = matcher.group(2).trim();

                switch (part) {
                    case "next":
                        withNextUrl(url);
                        break;
                    case "prev":
                        withPrevUrl(url);
                        break;
                    case "first":
                        withFirstUrl(url);
                        break;
                    case "last":
                        withLastUrl(url);
                        break;
                    default:
                        // Ignore.
                        break;
                }
            }
        }
        return this;
    }

    /**
     * Create new {@link PagingLinks} instance.
     * @return Create new {@link PagingLinks} instance.
     */
    public PagingLinks build() {
        return new PagingLinks(prevUrl, nextUrl, firstUrl, lastUrl);
    }
}
