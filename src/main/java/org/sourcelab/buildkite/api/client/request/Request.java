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

import org.sourcelab.buildkite.api.client.exception.ResponseParsingException;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.io.IOException;

/**
 * Defines an API request.
 * @param <T> The parsed return type representing the response.
 */
public interface Request<T> {
    /**
     * Which HTTP Method to make the request using.  GET, POST, PUT, etc...
     * @return Which HTTP Method to make the request using.  GET, POST, PUT, etc...
     */
    HttpMethod getMethod();

    /**
     * REST Endpoint Path to request.  Example "/v2/access-token"
     * @return REST Endpoint Path to request.  Example "/v2/access-token"
     */
    String getPath();

    /**
     * Defines how to parse the APIs response into a concrete object.
     * @return Defines how to parse the APIs response into a concrete object.
     */
    ResponseParser<T> getResponseParser();

    /**
     * Parses the API's response into a concrete object.
     *
     * @param result The API's response.
     * @return Parsed response object.
     * @throws ResponseParsingException if unable to properly parse a response from the API.
     */
    default T parseResponse(final HttpResult result) {
        try {
            return getResponseParser().parseResponse(result);
        } catch (final IOException parseException) {
            final String message = "Unable to parse response from API: " + parseException.getMessage() + "\n"
                    + "Response From API: \n"
                    + result.getContent();
            throw new ResponseParsingException(message, parseException);
        }
    }
}