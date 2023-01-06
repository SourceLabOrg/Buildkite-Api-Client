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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Collection of {@link RequestParameter} entries.
 */
public class RequestParameters {
    final Map<String, RequestParameter> parameters;

    /**
     * Builder instance.
     * @return new Builder instance.
     */
    public static RequestParametersBuilder newBuilder() {
        return new RequestParametersBuilder();
    }

    /**
     * Constructor.
     * @param parameters Parameters to add to the set.
     */
    public RequestParameters(final Collection<RequestParameter> parameters) {
        final Map<String, RequestParameter> parameterMap = new HashMap<>();

        parameters.forEach((parameter) -> {
            Objects.requireNonNull(parameter.getName());
            if (parameterMap.containsKey(parameter.getName())) {
                throw new IllegalArgumentException("Parameter name '" + parameter.getName() + "' provided multiple times!");
            }
            parameterMap.put(parameter.getName(), parameter);
        });
        this.parameters = Collections.unmodifiableMap(parameterMap);
    }

    /**
     * Get all unique parameter names.
     * @return All unique parameter names.
     */
    public Set<String> getParameterNames() {
        // Return sorted set.
        return parameters.keySet()
            .stream()
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Check if the collection contains the parameter with the given name.
     * @param name Name of parameter to check.
     * @return true if contained in the set, false if not.
     */
    public boolean hasParameter(final String name) {
        return parameters.containsKey(name);
    }

    /**
     * All parameters defined in the collection.
     * @return All parameters defined in the collection.
     */
    public Collection<RequestParameter> getParameters() {
        return parameters.values();
    }

    /**
     * Get the parameter for the given name.
     * @param name Name of parameter to return.
     * @return The parameter named by the name argument.
     * @throws IllegalArgumentException if passed an invalid parameter name.
     */
    public RequestParameter getParameterByName(final String name) {
        Objects.requireNonNull(name);
        if (!hasParameter(name)) {
            throw new IllegalArgumentException("Parameter '" + name + "' not defined in set!");
        }
        return parameters.get(name);
    }

    @Override
    public String toString() {
        return "RequestParameters{"
            + "parameters=" + parameters
            + '}';
    }
}
