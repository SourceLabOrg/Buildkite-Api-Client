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

package org.sourcelab.buildkite.api.client.util;

import org.sourcelab.buildkite.api.client.BuildkiteClient;
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.PageOptions;
import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of Utilities for common access patterns that consists of multiple
 * requests to the Buildkite REST Api.
 */
public class BuildkiteClientUtils {

    /**
     * Helper method to retrieve the N **newest** builds given the supplied search criteria.
     * The results will be ordered from NEWEST to OLDEST.
     *
     * @param numberOfBuilds How many builds to retrieve, (artificially) limited to max of 100.
     * @param filters Search criteria.
     * @param client The BuildkiteClient to execute the requests against.
     * @return List of Builds sorted from NEWEST to OLDEST.
     */
    public static List<Build> retrieveNewestBuilds(final int numberOfBuilds, final BuildFilters filters, final BuildkiteClient client) {
        // Create request
        final ListBuildsRequest request = new ListBuildsRequest(filters);
        request.updatePageOptions(new PageOptions(1, 1));

        // Retrieve first entry only, to determine how many total entries there are.
        final ListBuildsResponse lookupResponse = client.executeRequest(request);
        final long totalNumberOfEntries = lookupResponse.getPagingLinks().getTotalNumberOfEntries();

        /**
         * Assumption is that we can only pull at max, the last 100 entries as that is the maximum that
         * the API can return in a single request.
         * This method could be updated pretty easily to support larger numbers, submit a PR :)
         */
        final long pageNumber  = totalNumberOfEntries / numberOfBuilds;
        request.updatePageOptions(new PageOptions(pageNumber, numberOfBuilds));

        // Execute the request for the correct page of results.
        final ListBuildsResponse response = client.executeRequest(request);

        // But the results are still sorted in oldest to newest, we want newest to oldest, so reverse the order
        // before returning.
        final List<Build> reversed = new ArrayList<>(response.getBuilds());
        return reversed;
    }
}
