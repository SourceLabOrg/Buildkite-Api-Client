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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Utility for constructing {@link RequestParameters}.
 */
public class RequestParametersBuilder {
    final Map<String, Set<String>> values = new HashMap<>();

    /**
     * Adds/Appends Request Parameter value.
     * @param name Name of the parameter to add/append value to.
     * @param values Values to add/append.
     * @return RequestParametersBuilder for method chaining.
     */
    public RequestParametersBuilder withParameter(final String name, final Collection<String> values) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(values);

        values.forEach((value) -> withParameter(name, value));
        return this;
    }

    /**
     * Adds/Appends Request Parameter value.
     * @param name Name of the parameter to add/append value to.
     * @param value Value to add/append.
     * @return RequestParametersBuilder for method chaining.
     */
    public RequestParametersBuilder withParameter(final String name, final Object value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);

        if (!values.containsKey(name)) {
            values.put(name, new HashSet<>());
        }
        values.get(name).add(value.toString());
        return this;
    }

    /**
     * Adds/Appends Request Parameter value.
     * @param name Name of the parameter to add/append value to.
     * @param values Values to append.
     * @return RequestParametersBuilder for method chaining.
     */
    public RequestParametersBuilder withParameter(final String name, final Object ... values) {
        Arrays.asList(values).forEach((value) -> withParameter(name, value));
        return this;
    }

    /**
     * New {@link RequestParameters} instance from configured values.
     * @return New {@link RequestParameters} instance from configured values.
     */
    public RequestParameters build() {
        final List<RequestParameter> params = new ArrayList<>();
        for (Map.Entry<String, Set<String>> param: values.entrySet()) {
            params.add(new RequestParameter(param.getKey(), param.getValue()));
        }
        return new RequestParameters(params);
    }

}
