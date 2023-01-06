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

public class Agent {
    private final String id;
    private final String graphqlId;
    private final String url;
    private final String webUrl;
    private final String name;
    private final String connectionState;
    private final String hostname;
    private final String ipAddress;
    private final String userAgent;
    
    private final ZonedDateTime createdAt;

    private final Creator creator;

    /**
     * Constructor.
     */
    @JsonCreator
    public Agent(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("url") final String url,
        @JsonProperty("web_url") final String webUrl,
        @JsonProperty("name") final String name,
        @JsonProperty("connection_state") final String connectionState,
        @JsonProperty("hostname") final String hostname,
        @JsonProperty("ip_address") final String ipAddress,
        @JsonProperty("user_agent") final String userAgent,
        @JsonProperty("created_at") final ZonedDateTime createdAt,
        @JsonProperty("creator") final Creator creator
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.url = url;
        this.webUrl = webUrl;
        this.name = name;
        this.connectionState = connectionState;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
        this.creator = creator;
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

    public String getConnectionState() {
        return connectionState;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Creator getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Agent{"
            + "id='" + id + '\''
            + ", graphqlId='" + graphqlId + '\''
            + ", url='" + url + '\''
            + ", webUrl='" + webUrl + '\''
            + ", name='" + name + '\''
            + ", connectionState='" + connectionState + '\''
            + ", hostname='" + hostname + '\''
            + ", ipAddress='" + ipAddress + '\''
            + ", userAgent='" + userAgent + '\''
            + ", createdAt=" + createdAt
            + ", creator=" + creator
            + '}';
    }
}
