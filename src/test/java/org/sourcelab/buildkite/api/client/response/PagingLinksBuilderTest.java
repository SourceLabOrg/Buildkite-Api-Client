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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PagingLinksBuilderTest {

    /**
     * For various input values, check parsing.
     */
    @Test
    void testParse() {
        final String input = "<https://api.buildkite.com/v2/builds?page=2&per_page=2>; rel=\"next\", <https://api.buildkite.com/v2/builds?page=4&per_page=2>; rel=\"last\"";

        final PagingLinks result = PagingLinks.newBuilder()
            .fromHeaderLine(input)
            .build();

        assertNotNull(result);
        assertTrue(result.hasNextUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=2&per_page=2", result.getNextUrl());

        assertTrue(result.hasLastUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=4&per_page=2", result.getLastUrl());

        assertFalse(result.hasFirstUrl());
        assertFalse(result.hasPrevUrl());
    }

    /**
     * For various input values, check parsing.
     */
    @Test
    void testParse2() {
        final String input = "<https://api.buildkite.com/v2/builds?page=2&per_page=2>; rel=\"prev\", <https://api.buildkite.com/v2/builds?page=4&per_page=2>; rel=\"first\"";

        final PagingLinks result = PagingLinks.newBuilder()
                .fromHeaderLine(input)
                .build();

        assertNotNull(result);
        assertTrue(result.hasPrevUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=2&per_page=2", result.getPrevUrl());

        assertTrue(result.hasFirstUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=4&per_page=2", result.getFirstUrl());

        assertFalse(result.hasNextUrl());
        assertFalse(result.hasLastUrl());
    }

    /**
     * For various input values, check parsing.
     */
    @Test
    void testParse3() {
        final String input = "<https://api.buildkite.com/v2/builds?page=4&per_page=2>; rel=\"last\", <https://api.buildkite.com/v2/builds?page=1&per_page=2>; rel=\"first\", <https://api.buildkite.com/v2/builds?page=3&per_page=2>; rel=\"next\", <https://api.buildkite.com/v2/builds?page=2&per_page=2>; rel=\"prev\"";

        final PagingLinks result = PagingLinks.newBuilder()
                .fromHeaderLine(input)
                .build();

        assertNotNull(result);

        assertTrue(result.hasNextUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=3&per_page=2", result.getNextUrl());

        assertTrue(result.hasLastUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=4&per_page=2", result.getLastUrl());

        assertTrue(result.hasPrevUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=2&per_page=2", result.getPrevUrl());

        assertTrue(result.hasFirstUrl());
        assertEquals("https://api.buildkite.com/v2/builds?page=1&per_page=2", result.getFirstUrl());
    }
}