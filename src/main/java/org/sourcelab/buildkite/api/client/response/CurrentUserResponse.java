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

/**
 * Represents response from the AccessToken API Endpoint.
 * @see <a href="https://buildkite.com/docs/apis/rest-api/user#get-the-current-user">https://buildkite.com/docs/apis/rest-api/user#get-the-current-user</a>
 */
public class CurrentUserResponse {
    private final String id;
    private final String graphqlId;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final ZonedDateTime createdAt;

    /**
     * Constructor.
     * @param id Id property.
     * @param graphqlId graphqlId property.
     * @param name name property.
     * @param email email property.
     * @param avatarUrl avatarUrl property.
     * @param createdAt createdAt timestamp;
     */
    @JsonCreator
    public CurrentUserResponse(
        @JsonProperty("id") final String id,
        @JsonProperty("graphql_id") final String graphqlId,
        @JsonProperty("name") final String name,
        @JsonProperty("email") final String email,
        @JsonProperty("avatar_url") final String avatarUrl,
        @JsonProperty("created_at") final ZonedDateTime createdAt
    ) {
        this.id = id;
        this.graphqlId = graphqlId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getGraphqlId() {
        return graphqlId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "CurrentUserResponse{"
            + "id='" + id + '\''
            + ", graphqlId='" + graphqlId + '\''
            + ", name='" + name + '\''
            + ", email='" + email + '\''
            + ", avatarUrl='" + avatarUrl + '\''
            + ", createdAt='" + createdAt + '\''
            + '}';
    }
}
