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

package org.sourcelab.buildkite.api.client.http;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Collection of Zero or more {@link HttpHeader} entries.
 */
public class HttpHeaders {
    private final Map<String, HttpHeader> headerMap;

    /**
     * Constructor.
     * @param headers Header values.
     */
    public HttpHeaders(final Collection<HttpHeader> headers) {
        Objects.requireNonNull(headers);

        final Map<String, HttpHeader> headerMap = new HashMap<>();
        headers.forEach((header) -> {
            Objects.requireNonNull(header.getName());
            if (headerMap.containsKey(header.getName())) {
                // NOTE: this will overwrite with last value wins... I think that is OK for now.
                //throw new IllegalArgumentException("Header '" + header.getName() + "' defined multiple times!");
            }
            headerMap.put(header.getName(), header);
        });
        this.headerMap = Collections.unmodifiableMap(headerMap);
    }

    /**
     * All header names.
     * @return All header names.
     */
    public Set<String> getHeaderNames() {
        return headerMap.keySet()
            .stream()
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * All header names.
     * @return All header names.
     */
    public boolean hasHeader(final String name) {
        return headerMap.containsKey(name);
    }

    /**
     * Get a header value by name.
     * @param name Name of the header to get value for.
     * @return Header's value.
     */
    public String getHeader(final String name) {
        if (!hasHeader(name)) {
            throw new IllegalArgumentException("Header '" + name + "' does not exist.");
        }
        return headerMap.get(name).getValue();
    }

    @Override
    public String toString() {
        return "HttpHeaders{"
            + headerMap
            + '}';
    }
}
