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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageOptionsTest {

    @Test
    void getDefault() {
        final PageOptions pageOptions = PageOptions.getDefault();
        assertEquals(1, pageOptions.getPage());
        assertEquals(30, pageOptions.getPerPage());
    }

    @Test
    void fromUrl() {
        final String input = "https://api.buildkite.com/v2/build?page=2&per_page=3&something=value";
        final PageOptions pageOptions = PageOptions.fromUrl(input);
        assertEquals(2, pageOptions.getPage());
        assertEquals(3, pageOptions.getPerPage());
    }

    @Test
    void fromUrl2() {
        final String input = "https://api.buildkite.com/v2/build?per_page=2&page=3&something=value";
        final PageOptions pageOptions = PageOptions.fromUrl(input);
        assertEquals(3, pageOptions.getPage());
        assertEquals(2, pageOptions.getPerPage());
    }

    @Test
    void fromUrl3() {
        final String input = "https://api.buildkite.com/v2/build?per_page=202&page=303&something=value";
        final PageOptions pageOptions = PageOptions.fromUrl(input);
        assertEquals(303, pageOptions.getPage(), "Invalid page value");

        // Per Page maxes out at 100,
        assertEquals(100, pageOptions.getPerPage(), "invalid per page value");
    }

    @Test
    void fromUrl4() {
        final String input = "https://api.buildkite.com/v2/build?per_page=92&page=303&something=value";
        final PageOptions pageOptions = PageOptions.fromUrl(input);
        assertEquals(303, pageOptions.getPage(), "Invalid page value");
        assertEquals(92, pageOptions.getPerPage(), "invalid per page value");
    }
}