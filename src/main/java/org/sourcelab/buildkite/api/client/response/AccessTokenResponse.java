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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents response from the AccessToken API Endpoint.
 * @see <a href="https://buildkite.com/docs/apis/rest-api/access-token">https://buildkite.com/docs/apis/rest-api/access-token</a>
 */
public class AccessTokenResponse {
    private final String uuid;
    private final List<String> scopes;

    /**
     * Constructor.
     * @param uuid UUID value.
     * @param scopes Scopes value.
     */
    @JsonCreator
    public AccessTokenResponse(@JsonProperty("uuid") final String uuid, @JsonProperty("scopes") final List<String> scopes) {
        this.uuid = uuid;
        if (scopes == null) {
            this.scopes = Collections.unmodifiableList(new ArrayList<>());
        } else {
            // Sort the incoming scopes.
            this.scopes = Collections.unmodifiableList(scopes.stream().sorted().collect(Collectors.toList()));
        }
    }

    /**
     * The UUID value associated with the current access token.
     * @return The UUID value associated with the current access token.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Scopes associated with the current access token.
     * @return Scopes associated with the current access token.
     */
    public List<String> getScopes() {
        return scopes;
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{"
            + "uuid='" + uuid + '\''
            + ", scopes=" + scopes
            + '}';
    }
}
