/**
 * Copyright 2023 SourceLab.org https://github.com/SourceLabOrg/Buildkite-Api-Client
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.buildkite.api.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class Annotation {
    private final String id;
    private final String context;
    private final String style;
    private final String bodyHtml;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

    /**
     * Constructor.
     */
    @JsonCreator
    public Annotation(
        @JsonProperty("id") final String id,
        @JsonProperty("context") final String context,
        @JsonProperty("style") final String style,
        @JsonProperty("body_html") final String bodyHtml,
        @JsonProperty("created_at") final ZonedDateTime createdAt,
        @JsonProperty("updated_at") final ZonedDateTime updatedAt
    ) {
        this.id = id;
        this.context = context;
        this.style = style;
        this.bodyHtml = bodyHtml;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getContext() {
        return context;
    }

    public String getStyle() {
        return style;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Annotation{"
            + "id='" + id + '\''
            + ", context='" + context + '\''
            + ", style='" + style + '\''
            + ", bodyHtml='" + bodyHtml + '\''
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
    }
}
