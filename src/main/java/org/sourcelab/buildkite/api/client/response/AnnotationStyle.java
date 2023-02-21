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

/**
 * Represents the various "style" values that can be set on an Annotation.
 */
public enum AnnotationStyle {
    success, info, warning, error, unknown;

    /**
     * Factory method.  Go from the String response to the Enum.
     * If passed null, this will throw a NullPointerException.
     * If passed a value that doesn't match with the known enum types, it will be converted to 'unknown'
     *
     * @param type String representation to convert to enum value.
     * @return enum value representing the input string.
     * @throws NullPointerException if passed null.
     */
    public static AnnotationStyle fromString(final String type) {
        Objects.requireNonNull(type);

        switch (type.trim().toLowerCase()) {
            case "success":
                return success;
            case "info":
                return info;
            case "warning":
                return warning;
            case "error":
                return error;
            default:
                return unknown;
        }
    }
}
