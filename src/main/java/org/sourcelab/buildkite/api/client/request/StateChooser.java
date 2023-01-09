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

public class StateChooser {
    private final BuildFiltersBuilder builder;

    public StateChooser(final BuildFiltersBuilder builder) {
        this.builder = builder;
    }

    public BuildFiltersBuilder blocked() {
        return builder.withState("blocked");
    }

    public StateChooser blockedAnd() {
        blocked();
        return this;
    }

    public BuildFiltersBuilder canceled() {
        return builder.withState("canceled");
    }

    public StateChooser canceledAnd() {
        canceled();
        return this;
    }

    public BuildFiltersBuilder canceling() {
        return builder.withState("canceling");
    }

    public StateChooser cancelingAnd() {
        canceling();
        return this;
    }

    public BuildFiltersBuilder failed() {
        return builder.withState("failed");
    }

    public StateChooser failedAnd() {
        builder.withState("failed");
        return this;
    }

    public BuildFiltersBuilder failing() {
        return builder.withState("failing");
    }

    public StateChooser failingAnd() {
        failing();
        return this;
    }

    public BuildFiltersBuilder finished() {
        return builder.withState("finished");
    }

    public StateChooser finishedAnd() {
        finished();
        return this;
    }

    public BuildFiltersBuilder notRun() {
        return builder.withState("not_run");
    }

    public StateChooser notRunAnd() {
        notRun();
        return this;
    }

    public BuildFiltersBuilder passed() {
        return builder.withState("passed");
    }

    public StateChooser passedAnd() {
        passed();
        return this;
    }

    public BuildFiltersBuilder running() {
        return builder.withState("running");
    }

    public StateChooser runningAnd() {
        running();
        return this;
    }

    public BuildFiltersBuilder scheduled() {
        return builder.withState("scheduled");
    }

    public StateChooser scheduledAnd() {
        scheduled();
        return this;
    }

    public BuildFiltersBuilder skipped() {
        return builder.withState("skipped");
    }

    public StateChooser skippedAnd() {
        skipped();
        return this;
    }
}
