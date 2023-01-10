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

import org.sourcelab.buildkite.api.client.response.Organization;
import org.sourcelab.buildkite.api.client.response.Pipeline;
import org.sourcelab.buildkite.api.client.response.parser.GetPipelineResponseParser;
import org.sourcelab.buildkite.api.client.response.parser.ResponseParser;

import java.util.Objects;

public class GetPipelineRequest extends GetRequest<Pipeline> {
    private final String orgIdSlug;
    private final String pipelineIdSlug;

    /**
     * Constructor.
     *
     * @param orgIdSlug Organization to retrieve pipeline for.
     * @param pipelineIdSlug Pipeline to retrieve.
     */
    public GetPipelineRequest(final String orgIdSlug, final String pipelineIdSlug) {
        this.pipelineIdSlug = Objects.requireNonNull(pipelineIdSlug);
        this.orgIdSlug = Objects.requireNonNull(orgIdSlug);;
    }

    @Override
    public String getPath() {
        return "/v2/organizations/" + orgIdSlug + "/pipelines/" + pipelineIdSlug;
    }

    @Override
    public ResponseParser<Pipeline> getResponseParser() {
        return new GetPipelineResponseParser();
    }
}
