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

import org.sourcelab.buildkite.api.client.response.BuildState;

/**
 * Helpful utilities around Build State field and the BuildState enum.
 */
public class BuildStateUtils {
    /**
     * Check if the provided BuildState value is able to be cancelled.
     *
     * @param state The state to check.
     * @return true if the state is cancellable, false if not.
     */
    public static boolean isStateCancellable(final BuildState state) {
        if (state == null) {
            return false;
        }
        switch (state) {
            case CREATING:
            case SCHEDULED:
            case FAILING:
            case RUNNING:
            case BLOCKED:
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if the provided BuildState value is considered "finished".
     *
     * @param state The state to check.
     * @return true if the state is considered "finished", false if not.
     */
    public static boolean isStateConsideredFinished(final BuildState state) {
        if (state == null) {
            return true;
        }

        switch (state) {
            case FAILED:
            case PASSED:
            case SKIPPED:
            case NOT_RUN:
            case BROKEN:
                return true;
            default:
                return false;
        }
    }
}
