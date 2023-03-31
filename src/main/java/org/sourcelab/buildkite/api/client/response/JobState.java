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

public enum JobState {
    SKIPPED,
    ACCEPTED,
    ASSIGNED,
    SCHEDULED,
    WAITING,
    PENDING,
    WAITING_FAILED,
    BLOCKED_FAILED,
    UNBLOCKED_FAILED,
    TIMED_OUT,
    BROKEN,
    FAILING,
    FAILED,
    LIMITING,
    LIMITED,
    BLOCKED,
    CANCELED,
    TIMING_OUT,
    RUNNING,
    UNBLOCKED,
    CANCELING,
    PASSED,
    FINISHED,
    NOT_RUN,

    // Fall through value.
    UNKNOWN;

    /**
     * Create enum from String value as returned from the API.
     * @param value the string representation.
     * @return Enum value, or UNKNOWN if passed an unhandled value.
     */
    public static JobState createFromString(final String value) {
        if (value == null) {
            return UNKNOWN;
        }
        switch (value.trim().toLowerCase()) {
            case "skipped":
                return SKIPPED;
            case "accepted":
                return ACCEPTED;
            case "assigned":
                return ASSIGNED;
            case "scheduled":
                return SCHEDULED;
            case "waiting":
                return WAITING;
            case "pending":
                return PENDING;

            case "waiting_failed":
                return WAITING_FAILED;
            case "blocked_failed":
                return BLOCKED_FAILED;
            case "unblocked_failed":
                return UNBLOCKED_FAILED;
            case "timed_out":
                return TIMED_OUT;
            case "broken":
                return BROKEN;
            case "failing":
                return FAILING;
            case "failed":
                return FAILED;

            case "limiting":
                return LIMITING;
            case "limited":
                return LIMITED;
            case "blocked":
                return BLOCKED;
            case "canceled":
                return CANCELED;
            case "timing_out":
                return TIMING_OUT;

            case "running":
                return RUNNING;
            case "unblocked":
                return UNBLOCKED;
            case "canceling":
                return CANCELING;

            case "passed":
                return PASSED;
            case "finished":
                return FINISHED;
            case "not_run":
                return NOT_RUN;

            default:
                return UNKNOWN;
        }
    }

}
