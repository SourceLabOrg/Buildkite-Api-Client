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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Provider {
    private final String id;
    private final String webhookUrl;
    private final Map<String, String> settings;

    /**
     * Constructor.
     * @param id Id property.
     * @param webhookUrl Webhook Url property.
     */
    @JsonCreator
    public Provider(
        @JsonProperty("id") final String id,
        @JsonProperty("webhook_url") final String webhookUrl,
        @JsonProperty("settings") final Map<String, String> settings
    ) {
        this.id = id;
        this.webhookUrl = webhookUrl;

        final Map<String, String> settingsMap = new HashMap<>();
        if (settings != null) {
            settingsMap.putAll(settings);
        }
        this.settings = Collections.unmodifiableMap(settingsMap);
    }

    public String getId() {
        return id;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        return "Provider{"
            + "id='" + id + '\''
            + ", webhookUrl='" + webhookUrl + '\''
            + ", settings=" + settings
            + '}';
    }
}
