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

package org.sourcelab.buildkite.api.client.exception;

import org.sourcelab.buildkite.api.client.response.Error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thrown if request submitted to API comes back as invalid.
 */
public class InvalidRequestException extends BuildkiteException {
    private final List<Error> errors;

     /**
     * Constructor.
     * @param message Error message.
      */
    public InvalidRequestException(final String message) {
        this(message, Collections.emptyList());
    }

    /**
     * Constructor.
     * @param message Error message.
     * @param cause Underlying error cause.
     */
    public InvalidRequestException(final String message, final Exception cause) {
        super(message, cause);
        errors = Collections.emptyList();
    }

    /**
     * Constructor.
     * @param message Error message.
     * @param errors Underlying errors, if any.
     */
    public InvalidRequestException(final String message, final List<Error> errors) {
        super(message);
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    /**
     * Get errors associated with the request.
     * @return Errors associated with the request.
     */
    public List<Error> getErrors() {
        return errors;
    }
}
