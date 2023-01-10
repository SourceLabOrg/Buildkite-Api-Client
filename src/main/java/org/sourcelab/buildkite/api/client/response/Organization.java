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

import java.time.ZonedDateTime;

public class Organization {
    private final String id;
    private final String graphqlId;
    private final String url;
    private final String webUrl;
    private final String name;
    private final String slug;
    private final String pipelinesUrl;
    private final String emojisUrl;
    private final ZonedDateTime createdAt;

    /**
     * Constructor.
     */
    @JsonCreator
    public Organization(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("url") final String url,
        @JsonProperty("web_url") final String webUrl,
        @JsonProperty("name") final String name,
        @JsonProperty("slug") final String slug,
        @JsonProperty("pipelines_url") final String pipelinesUrl,
        @JsonProperty("emojis_url") final String emojisUrl,
        @JsonProperty("created_at") final ZonedDateTime createdAt
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.url = url;
        this.webUrl = webUrl;
        this.name = name;
        this.slug = slug;
        this.pipelinesUrl = pipelinesUrl;
        this.emojisUrl = emojisUrl;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getGraphqlId() {
        return graphqlId;
    }

    public String getUrl() {
        return url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getPipelinesUrl() {
        return pipelinesUrl;
    }

    public String getEmojisUrl() {
        return emojisUrl;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Organization{"
            + "id='" + id + '\''
            + ", graphqlId='" + graphqlId + '\''
            + ", url='" + url + '\''
            + ", webUrl='" + webUrl + '\''
            + ", name='" + name + '\''
            + ", slug='" + slug + '\''
            + ", pipelinesUrl='" + pipelinesUrl + '\''
            + ", emojisUrl='" + emojisUrl + '\''
            + ", createdAt=" + createdAt
            + '}';
    }
}
