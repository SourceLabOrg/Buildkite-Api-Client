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

import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.parser.GetBuildResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

public class CancelBuildRequest extends PutRequest<Build> {
    private final String orgIdSlug;
    private final String pipelineIdSlug;
    private final long buildNumber;

    /**
     * Constructor.
     */
    public CancelBuildRequest(final String orgIdSlug, final String pipelineIdSlug, final long buildNumber) {
        this.orgIdSlug = orgIdSlug;
        this.pipelineIdSlug = pipelineIdSlug;
        this.buildNumber = buildNumber;
    }

    @Override
    public String getPath() {
        return "/v2/organizations/" + orgIdSlug
            + "/pipelines/" + pipelineIdSlug
            + "/builds/" + buildNumber + "/cancel";

    }

    @Override
    public ResponseParser<Build> getResponseParser() {
        return new GetBuildResponseParser();
    }
}
