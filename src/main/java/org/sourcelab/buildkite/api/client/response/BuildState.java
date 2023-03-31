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

public enum BuildState {
    CREATING,
    SCHEDULED,
    RUNNING,
    PASSED,
    CANCELING,
    CANCELED,
    FAILING,
    FAILED,
    BLOCKED,
    SKIPPED,
    NOT_RUN,
    BROKEN,
    // Fall through value
    UNKNOWN;

    /**
     * Create enum from String value as returned from the API.
     * @param value the string representation.
     * @return Enum value, or UNKNOWN if passed an unhandled value.
     */
    public static BuildState createFromString(final String value) {
        if (value == null) {
            return UNKNOWN;
        }

        switch (value.trim().toLowerCase()) {
            case "creating":
                return CREATING;
            case "scheduled":
                return SCHEDULED;
            case "running":
                return RUNNING;
            case "passed":
                return PASSED;
            case "canceling":
                return CANCELING;
            case "canceled":
                return CANCELED;
            case "failing":
                return FAILING;
            case "failed":
                return FAILED;
            case "blocked":
                return BLOCKED;
            case "skipped":
                return SKIPPED;
            case "not_run":
                return NOT_RUN;
            case "broken":
                return BROKEN;
            default:
                return UNKNOWN;
        }
    }
}
