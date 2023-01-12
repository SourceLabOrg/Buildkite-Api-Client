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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RebuiltFrom {
    private final String id;
    private final long number;
    private final String url;

    /**
     * Constructor.
     */
    @JsonCreator
    public RebuiltFrom(
        @JsonProperty("id") final String id,
        @JsonProperty("number") final long number,
        @JsonProperty("url") final String url
    ) {
        this.id = id;
        this.number = number;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public long getNumber() {
        return number;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RebuiltFrom{"
            + "id='" + id + '\''
            + ", number=" + number
            + ", url='" + url + '\''
            + '}';
    }
}
