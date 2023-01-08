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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step {
    private final String type;
    private final String name;
    private final String command;
    private final String artifactPaths;
    private final String branchConfiguration;
    private final Map<String, String> env;
    private final long timeoutInMinutes;
    private final List<String> agentQueryRules;

    /**
     * Constructor.
     */
    @JsonCreator
    public Step(
        @JsonProperty("type") final String type,
        @JsonProperty("name") final String name,
        @JsonProperty("command") final String command,
        @JsonProperty("artifact_paths") final String artifactPaths,
        @JsonProperty("branch_configuration") final String branchConfiguration,
        @JsonProperty("env") final Map<String, String> env,
        @JsonProperty("timeout_in_minutes") final Long timeoutInMinutes,
        @JsonProperty("agent_query_rules") final List<String> agentQueryRules
    ) {
        this.type = type;
        this.name = name;
        this.command = command;
        this.artifactPaths = artifactPaths;
        this.branchConfiguration = branchConfiguration;
        this.timeoutInMinutes = timeoutInMinutes == null ? -1 : timeoutInMinutes;

        final Map<String, String> envMap = new HashMap<>();
        if (env != null) {
            envMap.putAll(env);
        }
        this.env = Collections.unmodifiableMap(envMap);

        final List<String> agentQueryRulesList = new ArrayList<>();
        if (agentQueryRules != null) {
            agentQueryRulesList.addAll(agentQueryRules);
        }
        this.agentQueryRules = Collections.unmodifiableList(agentQueryRulesList);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getArtifactPaths() {
        return artifactPaths;
    }

    public String getBranchConfiguration() {
        return branchConfiguration;
    }

    public Map<String, String> getEnv() {
        return env;
    }

    public long getTimeoutInMinutes() {
        return timeoutInMinutes;
    }

    public List<String> getAgentQueryRules() {
        return agentQueryRules;
    }

    @Override
    public String toString() {
        return "Step{"
            + "type='" + type + '\''
            + ", name='" + name + '\''
            + ", command='" + command + '\''
            + ", artifactPaths='" + artifactPaths + '\''
            + ", branchConfiguration='" + branchConfiguration + '\''
            + ", env=" + env
            + ", timeoutInMinutes=" + timeoutInMinutes
            + ", agentQueryRules=" + agentQueryRules
            + '}';
    }
}
