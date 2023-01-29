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

package org.sourcelab.buildkite.api.client.response.parser;

import org.junit.jupiter.api.Test;
import org.sourcelab.buildkite.api.client.MockResponseReader;
import org.sourcelab.buildkite.api.client.http.HttpHeaders;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.response.ErrorResponse;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ErrorResponseParserTest {

    /**
     * Standard error response
     */
    @Test
    void standardResponse() throws IOException {
        final HttpResult httpResult = createResultFromFile("errorResponse1.json");
        final ErrorResponse response = new ErrorResponseParser().parseResponse(httpResult);

        assertEquals("Error Happened", response.getMessage());
        assertNotNull(response.getErrors());
        assertTrue(response.getErrors().isEmpty());
    }

    /**
     * Error response with errors.
     */
    @Test
    void extendedResponse() throws IOException {
        final HttpResult httpResult = createResultFromFile("errorResponse2.json");
        final ErrorResponse response = new ErrorResponseParser().parseResponse(httpResult);

        assertEquals("Validation Failed", response.getMessage());
        assertNotNull(response.getErrors());
        assertFalse(response.getErrors().isEmpty());
        assertEquals(1, response.getErrors().size());

        assertEquals("commit", response.getErrors().get(0).getField());
        assertEquals("missing", response.getErrors().get(0).getCode());
    }

    private HttpResult createResultFromFile(final String fileName) {
        return new HttpResult(
            200,
            readFile(fileName),
            new HttpHeaders(Collections.emptyList())
        );
    }

    /**
     * Utility method to help load mock responses from resources.
     * @param fileName file name to load from resources
     * @return The contents of the file, as a UTF-8 string.
     * @throws RuntimeException on error reading from resource file.
     */
    private String readFile(final String fileName) {
        return MockResponseReader.readFile(fileName);
    }
}