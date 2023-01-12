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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.buildkite.api.client.exception.RequestParsingException;
import org.sourcelab.buildkite.api.client.response.Job;
import org.sourcelab.buildkite.api.client.response.parser.GetJobResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.JacksonFactory;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.util.Map;
import java.util.Objects;

public class UnblockJobRequest extends PutRequest<Job> {
    private final UnblockJobOptions options;

    /**
     * Constructor.
     */
    public UnblockJobRequest(final UnblockJobOptions options) {
        this.options = Objects.requireNonNull(options);
    }

    @Override
    public String getPath() {
        return "/v2/organizations/" + options.getOrganizationSlug()
            + "/pipelines/" + options.getPipelineSlug()
            + "/builds/" + options.getBuildNumber()
            + "/jobs/" + options.getJobId() + "/unblock";

    }

    @Override
    public String getRequestBody() throws RequestParsingException {
        try {
            final UnblockerRequestBody body = new UnblockerRequestBody(
                options.hasUnblockerUserId() ? options.getUnblockerUserId() : null,
                options.hasFields() ? options.getFields() : null
            );
            return JacksonFactory.newInstance().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RequestParsingException(e.getMessage(), e);
        }
    }

    @Override
    public ResponseParser<Job> getResponseParser() {
        return new GetJobResponseParser();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class UnblockerRequestBody {
        private String unblocker;
        private Map<String, String> fields;

        public UnblockerRequestBody(final String unblocker, final Map<String, String> fields) {
            this.unblocker = unblocker;
            this.fields = fields;
        }

        @JsonGetter("unblocker")
        public String getUnblocker() {
            return unblocker;
        }

        @JsonGetter("fields")
        public Map<String, String> getFields() {
            return fields;
        }
    }
}
