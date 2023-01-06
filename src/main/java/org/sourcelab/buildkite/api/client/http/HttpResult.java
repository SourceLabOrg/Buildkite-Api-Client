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

import java.util.Collections;

/**
 * Represents the Response from a Http Request.
 */
public class HttpResult {

    /**
     * Http Status Code.
     */
    final int status;

    /**
     * Response Body, in String form.
     */
    final String content;

    /**
     * Http Response Headers.
     */
    final HttpHeaders httpHeaders;

    /**
     * Constructor.
     * @param status Status code.
     * @param content String representation of the response.
     * @param httpHeaders Http response headers.
     */
    public HttpResult(final int status, final String content, final HttpHeaders httpHeaders) {
        this.status = status;
        this.content = content;
        this.httpHeaders = httpHeaders;
    }

    /**
     * Constructor.
     * @param status Status code.
     * @param content String representation of the response.
     */
    public HttpResult(final int status, final String content) {
        this(status, content, new HttpHeaders(Collections.emptyList()));
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    @Override
    public String toString() {
        return "HttpResult{"
            + "status=" + status
            + ", content='" + content + '\''
            + ", httpHeaders=" + httpHeaders
            + '}';
    }
}
