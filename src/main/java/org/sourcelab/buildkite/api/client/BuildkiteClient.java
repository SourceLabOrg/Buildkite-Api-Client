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

package org.sourcelab.buildkite.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.buildkite.api.client.exception.BuildkiteException;
import org.sourcelab.buildkite.api.client.exception.InvalidAccessTokenException;
import org.sourcelab.buildkite.api.client.exception.InvalidAllowedIpAddressException;
import org.sourcelab.buildkite.api.client.exception.InvalidPagingRequestException;
import org.sourcelab.buildkite.api.client.exception.InvalidRequestException;
import org.sourcelab.buildkite.api.client.exception.NotFoundException;
import org.sourcelab.buildkite.api.client.http.Client;
import org.sourcelab.buildkite.api.client.http.HttpResult;
import org.sourcelab.buildkite.api.client.request.AnnotationFilters;
import org.sourcelab.buildkite.api.client.request.BuildFilters;
import org.sourcelab.buildkite.api.client.request.BuildFiltersBuilder;
import org.sourcelab.buildkite.api.client.request.CancelBuildRequest;
import org.sourcelab.buildkite.api.client.request.CreateBuildOptions;
import org.sourcelab.buildkite.api.client.request.CreateBuildOptionsBuilder;
import org.sourcelab.buildkite.api.client.request.CreateBuildRequest;
import org.sourcelab.buildkite.api.client.request.DeleteAccessTokenRequest;
import org.sourcelab.buildkite.api.client.request.GetAccessTokenRequest;
import org.sourcelab.buildkite.api.client.request.GetAnnotationsForBuildRequest;
import org.sourcelab.buildkite.api.client.request.GetBuildFilters;
import org.sourcelab.buildkite.api.client.request.GetBuildFiltersBuilder;
import org.sourcelab.buildkite.api.client.request.GetBuildRequest;
import org.sourcelab.buildkite.api.client.request.GetMetaRequest;
import org.sourcelab.buildkite.api.client.request.GetOrganizationRequest;
import org.sourcelab.buildkite.api.client.request.GetPipelineRequest;
import org.sourcelab.buildkite.api.client.request.GetUserRequest;
import org.sourcelab.buildkite.api.client.request.ListBuildsRequest;
import org.sourcelab.buildkite.api.client.request.ListEmojisRequest;
import org.sourcelab.buildkite.api.client.request.ListOrganizationsRequest;
import org.sourcelab.buildkite.api.client.request.ListPipelinesRequest;
import org.sourcelab.buildkite.api.client.request.OrganizationFilters;
import org.sourcelab.buildkite.api.client.request.OrganizationFiltersBuilder;
import org.sourcelab.buildkite.api.client.request.PageOptions;
import org.sourcelab.buildkite.api.client.request.PageableRequest;
import org.sourcelab.buildkite.api.client.request.PingRequest;
import org.sourcelab.buildkite.api.client.request.PipelineFilters;
import org.sourcelab.buildkite.api.client.request.PipelineFiltersBuilder;
import org.sourcelab.buildkite.api.client.request.RebuildBuildRequest;
import org.sourcelab.buildkite.api.client.request.Request;
import org.sourcelab.buildkite.api.client.request.RetryJobOptions;
import org.sourcelab.buildkite.api.client.request.RetryJobOptionsBuilder;
import org.sourcelab.buildkite.api.client.request.RetryJobRequest;
import org.sourcelab.buildkite.api.client.request.UnblockJobOptions;
import org.sourcelab.buildkite.api.client.request.UnblockJobOptionsBuilder;
import org.sourcelab.buildkite.api.client.request.UnblockJobRequest;
import org.sourcelab.buildkite.api.client.response.AccessTokenResponse;
import org.sourcelab.buildkite.api.client.response.AnnotationsForBuildResponse;
import org.sourcelab.buildkite.api.client.response.Build;
import org.sourcelab.buildkite.api.client.response.CurrentUserResponse;
import org.sourcelab.buildkite.api.client.response.Emoji;
import org.sourcelab.buildkite.api.client.response.Error;
import org.sourcelab.buildkite.api.client.response.ErrorResponse;
import org.sourcelab.buildkite.api.client.response.Job;
import org.sourcelab.buildkite.api.client.response.ListBuildsResponse;
import org.sourcelab.buildkite.api.client.response.ListOrganizationsResponse;
import org.sourcelab.buildkite.api.client.response.ListPipelinesResponse;
import org.sourcelab.buildkite.api.client.response.MetaResponse;
import org.sourcelab.buildkite.api.client.response.Organization;
import org.sourcelab.buildkite.api.client.response.PageableResponse;
import org.sourcelab.buildkite.api.client.response.PagingLinks;
import org.sourcelab.buildkite.api.client.response.PingResponse;
import org.sourcelab.buildkite.api.client.response.Pipeline;
import org.sourcelab.buildkite.api.client.response.parser.ErrorResponseParser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * API Client for Buildkite's REST Api.
 *
 * See API Documentation: {@see <a href="https://buildkite.com/docs/apis/rest-api">https://buildkite.com/docs/apis/rest-api</a>}
 */
public class BuildkiteClient {
    private static final Logger logger = LoggerFactory.getLogger(BuildkiteClient.class);

    /**
     * User provided configuration.
     */
    private final Configuration configuration;

    /**
     * Underlying HTTP client.
     */
    private final Client httpClient;

    /**
     * Constructor.
     * @param configuration The configuration for the client.
     */
    public BuildkiteClient(final Configuration configuration) {
        this.configuration = configuration;
        this.httpClient = configuration.getClientFactory().createClient(configuration);
    }

    /**
     * Make a 'test' or 'hello world' request to the Buildkite API.  Can be used to validate
     * connectivity to the API.
     * @see <a href="https://buildkite.com/docs/apis/rest-api">https://buildkite.com/docs/apis/rest-api</a>
     *
     * @return Response details from the ping request.
     * @throws BuildkiteException if API returns an error response.
     */
    public PingResponse ping() throws BuildkiteException {
        return executeRequest(new PingRequest());
    }

    /**
     * Retrieve details about the current AccessToken.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/access-token">https://buildkite.com/docs/apis/rest-api/access-token</a>
     *
     * @return Details about the current AccessToken.
     * @throws BuildkiteException if API returns an error response.
     */
    public AccessTokenResponse getAccessToken() throws BuildkiteException {
        return executeRequest(new GetAccessTokenRequest());
    }

    /**
     * Deletes/Revokes the current AccessToken.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/access-token#revoke-the-current-token">https://buildkite.com/docs/apis/rest-api/access-token#revoke-the-current-token</a>
     *
     * @return Details about the current AccessToken.
     * @throws BuildkiteException if API returns an error response.
     */
    public boolean deleteAccessToken() throws BuildkiteException {
        return executeRequest(new DeleteAccessTokenRequest());
    }

    /**
     * Retrieves the current user associated with the current access token.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/user#get-the-current-user">https://buildkite.com/docs/apis/rest-api/user#get-the-current-user</a>
     *
     * @return Details about the current User.
     * @throws BuildkiteException if API returns an error response.
     */
    public CurrentUserResponse getUser() throws BuildkiteException {
        return executeRequest(new GetUserRequest());
    }

    /**
     * Retrieve all Organizations accessible to the current user/API access token.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/organizations#list-organizations">https://buildkite.com/docs/apis/rest-api/organizations#list-organizations</a>
     *
     * @return All Organizations accessible to the current user/API access token. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListOrganizationsResponse listOrganizations() throws BuildkiteException {
        return listOrganizations(OrganizationFilters.newBuilder());
    }

    /**
     * Retrieve all Organizations accessible to the current user/API access token.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/organizations#list-organizations">https://buildkite.com/docs/apis/rest-api/organizations#list-organizations</a>
     *
     * @param filters Filter criteria.
     * @return All Organizations accessible to the current user/API access token. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListOrganizationsResponse listOrganizations(final OrganizationFiltersBuilder filters) throws BuildkiteException {
        Objects.requireNonNull(filters);
        return listOrganizations(filters.build());
    }

    /**
     * Retrieve all Organizations accessible to the current user/API access token.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/organizations#list-organizations">https://buildkite.com/docs/apis/rest-api/organizations#list-organizations</a>
     *
     * @param filters Filter criteria.
     * @return All Organizations accessible to the current user/API access token. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListOrganizationsResponse listOrganizations(final OrganizationFilters filters) throws BuildkiteException {
        Objects.requireNonNull(filters);
        return executeRequest(new ListOrganizationsRequest(filters));
    }

    /**
     * Retrieve specific organization via its Organization slug id.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/organizations#get-an-organization">https://buildkite.com/docs/apis/rest-api/organizations#get-an-organization</a>
     *
     * @param organizationSlugId Slug of the organization to retrieve.
     * @return Organization matching the slug, if found.
     * @throws BuildkiteException if API returns an error response.
     */
    public Optional<Organization> getOrganization(final String organizationSlugId) throws BuildkiteException {
        Objects.requireNonNull(organizationSlugId);
        final Organization response = executeRequest(new GetOrganizationRequest(organizationSlugId));
        return Optional.ofNullable(response);
    }

    /**
     * Retrieve all Pipeline accessible to the current user/API access token for the given Organization.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines">https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines</a>
     *
     * @param filters Filter criteria.
     * @return All Pipelines accessible to the current user/API access token. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListPipelinesResponse listPipelines(final PipelineFiltersBuilder filters) throws BuildkiteException {
        Objects.requireNonNull(filters);
        return listPipelines(filters.build());
    }

    /**
     * Retrieve all Pipelines accessible to the current user/API access token for the given Organization.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines">https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines</a>
     *
     * @param filters Filter criteria.
     * @return All Pipelines accessible to the current user/API access token. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListPipelinesResponse listPipelines(final PipelineFilters filters) throws BuildkiteException {
        Objects.requireNonNull(filters);
        return executeRequest(new ListPipelinesRequest(filters));
    }

    /**
     * Retrieve all Pipelines accessible to the current user/API access token for the given Organization.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines">https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines</a>
     *
     * @param organizationSlugId Organization Slug Id to retrieve pipelines for.
     * @return All Pipelines accessible to the current user/API access token for the given Organization. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListPipelinesResponse listPipelines(final String organizationSlugId) throws BuildkiteException {
        Objects.requireNonNull(organizationSlugId);
        return listPipelines(PipelineFilters.newBuilder()
            .withOrganization(organizationSlugId)
        );
    }

    /**
     * Retrieve specific pipeline via its Organization and Pipeline Slug Ids.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/pipelines#get-a-pipeline">https://buildkite.com/docs/apis/rest-api/pipelines#get-a-pipeline</a>
     *
     * @param organizationSlugId Slug of the organization to retrieve.
     * @param pipelineSlugId Slug of the pipeline to retrieve.
     * @return Pipeline matching the slug, if found.
     * @throws BuildkiteException if API returns an error response.
     */
    public Optional<Pipeline> getPipeline(final String organizationSlugId, final String pipelineSlugId) throws BuildkiteException {
        Objects.requireNonNull(organizationSlugId);
        Objects.requireNonNull(pipelineSlugId);
        final Pipeline pipeline = executeRequest(new GetPipelineRequest(organizationSlugId, pipelineSlugId));
        return Optional.ofNullable(pipeline);
    }

    /**
     * Retrieve all Builds accessible to the current user/API access token, across all Organizations.
     * Results will be paged.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#list-all-builds">https://buildkite.com/docs/apis/rest-api/builds#list-all-builds</a>
     *
     * @return All Builds accessible to the current user/API access token, across all Organizations. Results
     *         will be paged if the number of results exceeds 30.
     * @throws BuildkiteException if API returns an error response.
     */
    public ListBuildsResponse listBuilds() {
        return listBuilds(BuildFilters.newBuilder().build());
    }

    /**
     * Retrieve all builds which match the supplied search criteria.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#list-all-builds">https://buildkite.com/docs/apis/rest-api/builds#list-all-builds</a>
     *
     * @param filtersBuilder Filter criteria.
     * @return All builds which match the supplied search criteria. Results will be paged if the number of results
     *         exceeds 30 (or the page limit specified in the search criteria).
     * @throws BuildkiteException if API returns an error response.
     */
    public ListBuildsResponse listBuilds(final BuildFiltersBuilder filtersBuilder) {
        return listBuilds(filtersBuilder.build());
    }

    /**
     * Retrieve all builds which match the supplied search criteria.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#list-all-builds">https://buildkite.com/docs/apis/rest-api/builds#list-all-builds</a>
     *
     * @param filters Filter criteria.
     * @return All builds which match the supplied search criteria. Results will be paged if the number of results
     *         exceeds 30 (or the page limit specified in the search criteria).
     * @throws BuildkiteException if API returns an error response.
     */
    public ListBuildsResponse listBuilds(final BuildFilters filters) {
        return executeRequest(new ListBuildsRequest(filters));
    }

    /**
     * Retrieve a specific build based on the filter criteria.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#get-a-build">https://buildkite.com/docs/apis/rest-api/builds#get-a-build</a>
     *
     * @param filters Filter criteria.
     * @return The build which matches the criteria if found.
     * @throws BuildkiteException if API returns an error response.
     */
    public Optional<Build> getBuild(final GetBuildFiltersBuilder filters) {
        return getBuild(filters.build());
    }

    /**
     * Retrieve a specific build based on the filter criteria.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#get-a-build">https://buildkite.com/docs/apis/rest-api/builds#get-a-build</a>
     *
     * @param organizationSlugId Organization associated with the build.
     * @param pipelineSlugId Pipeline associated with the build.
     * @param buildNumber The build number.
     * @return The build which matches the criteria if found.
     * @throws BuildkiteException if API returns an error response.
     */
    public Optional<Build> getBuild(final String organizationSlugId, final String pipelineSlugId, final long buildNumber) {
        return getBuild(GetBuildFilters.newBuilder()
                .withOrgIdSlug(organizationSlugId)
                .withPipelineIdSlug(pipelineSlugId)
                .withBuildNumber(buildNumber)
        );
    }

    /**
     * Retrieve a specific build based on the filter criteria.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#get-a-build">https://buildkite.com/docs/apis/rest-api/builds#get-a-build</a>
     *
     * @param filters Filter criteria.
     * @return The build which matches the criteria if found.
     * @throws BuildkiteException if API returns an error response.
     */
    public Optional<Build> getBuild(final GetBuildFilters filters) {
        final Build build = executeRequest(new GetBuildRequest(filters));
        return Optional.ofNullable(build);
    }

    /**
     * Cancels the build if its state is either scheduled or running.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#cancel-a-build">https://buildkite.com/docs/apis/rest-api/builds#cancel-a-build</a>
     *
     * @param organizationSlugId Organization associated with the build.
     * @param pipelineSlugId Pipeline associated with the build.
     * @param buildNumber The build number.
     * @return Updated Build instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Build cancelBuild(final String organizationSlugId, final String pipelineSlugId, final long buildNumber) {
        return executeRequest(new CancelBuildRequest(organizationSlugId, pipelineSlugId, buildNumber));
    }

    /**
     * Creates a new build to be executed.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#create-a-build">https://buildkite.com/docs/apis/rest-api/builds#create-a-build</a>
     *
     * @param createBuildOptions Defines the build to be created.
     * @return Created Build instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Build createBuild(final CreateBuildOptionsBuilder createBuildOptions) {
        return createBuild(createBuildOptions.build());
    }

    /**
     * Creates a new build to be executed.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#create-a-build">https://buildkite.com/docs/apis/rest-api/builds#create-a-build</a>
     *
     * @param createBuildOptions Defines the build to be created.
     * @return Created Build instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Build createBuild(final CreateBuildOptions createBuildOptions) {
        return executeRequest(new CreateBuildRequest(createBuildOptions));
    }

    /**
     * Retries a build.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/builds#rebuild-a-build">https://buildkite.com/docs/apis/rest-api/builds#rebuild-a-build</a>
     *
     * @param organizationSlugId Organization associated with the build.
     * @param pipelineSlugId Pipeline associated with the build.
     * @param buildNumber The build number.
     * @return Updated Build instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Build rebuildBuild(final String organizationSlugId, final String pipelineSlugId, final long buildNumber) {
        return executeRequest(new RebuildBuildRequest(organizationSlugId, pipelineSlugId, buildNumber));
    }

    /**
     * Retries a failed or timed_out job. You can only retry each job.id once.
     * To retry a "second time" use the new job.id returned in the first retry query.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/jobs#retry-a-job">https://buildkite.com/docs/apis/rest-api/jobs#retry-a-job</a>
     *
     * @param options Defines which Job to retry.
     * @return Updated job instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Job retryJob(final RetryJobOptionsBuilder options) {
        return retryJob(options.build());
    }

    /**
     * Retries a failed or timed_out job. You can only retry each job.id once.
     * To retry a "second time" use the new job.id returned in the first retry query.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/jobs#retry-a-job">https://buildkite.com/docs/apis/rest-api/jobs#retry-a-job</a>
     *
     * @param options Defines which Job to retry.
     * @return Updated job instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Job retryJob(final RetryJobOptions options) {
        return executeRequest(new RetryJobRequest(options));
    }

    /**
     * Unblocks a build's "Block pipeline" job. The job's unblockable property indicates whether it is able to be unblocked,
     * and the unblock_url property points to this endpoint.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/jobs#unblock-a-job">https://buildkite.com/docs/apis/rest-api/jobs#unblock-a-job</a>
     *
     * @param options Defines which Job to unblock.
     * @return Updated job instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Job unblockJob(final UnblockJobOptionsBuilder options) {
        return unblockJob(options.build());
    }

    /**
     * Unblocks a build's "Block pipeline" job. The job's unblockable property indicates whether it is able to be unblocked,
     * and the unblock_url property points to this endpoint.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/jobs#unblock-a-job">https://buildkite.com/docs/apis/rest-api/jobs#unblock-a-job</a>
     *
     * @param options Defines which Job to unblock.
     * @return Updated job instance.
     * @throws BuildkiteException if API returns an error response.
     */
    public Job unblockJob(final UnblockJobOptions options) {
        return executeRequest(new UnblockJobRequest(options));
    }

    /**
     * List all Emojis defined in the given Organization.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/emojis#list-emojis">https://buildkite.com/docs/apis/rest-api/emojis#list-emojis</a>
     *
     * @param orgIdSlug Organization Id slug to retrieve list of emojis for.
     * @return List of Emojis.
     * @throws BuildkiteException if API returns an error response.
     */
    public List<Emoji> listEmojis(final String orgIdSlug) {
        return executeRequest(new ListEmojisRequest(orgIdSlug));
    }

    /**
     * Retrieve annotations for a given build.
     *
     * @see <a href="https://buildkite.com/docs/apis/rest-api/annotations#list-annotations-for-a-build">https://buildkite.com/docs/apis/rest-api/annotations#list-annotations-for-a-build</a>
     *
     * @param options Defines which build to retrieve annotations for.
     * @return Annotations associated with the build.
     * @throws BuildkiteException if API returns an error response.
     */
    public AnnotationsForBuildResponse getAnnotationsForBuild(final AnnotationFilters options) {
        return executeRequest(new GetAnnotationsForBuildRequest(options));
    }

    /**
     * Retrieves metadata endpoint.
     * @see <a href="https://buildkite.com/docs/apis/rest-api/meta#get-meta-information">https://buildkite.com/docs/apis/rest-api/meta#get-meta-information</a>
     *
     * @return Details about the current User.
     * @throws BuildkiteException if API returns an error response.
     */
    public MetaResponse getMeta() throws BuildkiteException {
        return executeRequest(new GetMetaRequest());
    }

    /**
     * Retrieve the next page of results from the previously retrieved request.
     *
     * @param <T> The parsed return object representing the result.
     * @param response Previously retrieved result/response to retrieve the next page of results for.
     * @return The next page of results.
     * @throws InvalidPagingRequestException if no next page exists to retrieve.
     * @throws BuildkiteException if API returns an error response.
     */
    public <T> T nextPage(final PageableResponse<T> response) {
        // Validate
        Objects.requireNonNull(response);
        final PagingLinks pagingLinks = Objects.requireNonNull(response.getPagingLinks());
        if (!pagingLinks.hasNextUrl()) {
            throw new InvalidPagingRequestException(
                "Requested 'Next' page on response " + response.getClass().getSimpleName() + ", but no Next page is available."
            );
        }

        // Update request with appropriate page options.
        final PageOptions pageOptions;
        try {
            pageOptions = PageOptions.fromUrl(pagingLinks.getNextUrl());
        } catch (final IllegalArgumentException ex) {
            throw new InvalidPagingRequestException("Unable to parse URL for paging information", ex);
        }
        final PageableRequest<T> request = response.getOriginalRequest();
        request.updatePageOptions(pageOptions);

        // Execute and return.
        return executeRequest(request);
    }

    /**
     * Retrieve the previous page of results from the previously retrieved request.
     *
     * @param <T> The parsed return object representing the result.
     * @param response Previously retrieved result/response to retrieve the previous page of results for.
     * @return The previous page of results.
     * @throws InvalidPagingRequestException if no previous page exists to retrieve.
     * @throws BuildkiteException if API returns an error response.
     */
    public <T> T previousPage(final PageableResponse<T> response) {
        // Validate
        Objects.requireNonNull(response);
        final PagingLinks pagingLinks = Objects.requireNonNull(response.getPagingLinks());
        if (!pagingLinks.hasPrevUrl()) {
            throw new InvalidPagingRequestException(
                "Requested 'Previous' page on response " + response.getClass().getSimpleName() + ", but no Previous page is available."
            );
        }

        // Update request with appropriate page options.
        final PageOptions pageOptions;
        try {
            pageOptions = PageOptions.fromUrl(pagingLinks.getNextUrl());
        } catch (final IllegalArgumentException ex) {
            throw new InvalidPagingRequestException("Unable to parse URL for paging information", ex);
        }
        final PageableRequest<T> request = response.getOriginalRequest();
        request.updatePageOptions(pageOptions);

        // Execute and return.
        return executeRequest(request);
    }

    /**
     * Retrieve the first page of results from the previously retrieved request.
     *
     * @param <T> The parsed return object representing the result.
     * @param response Previously retrieved result/response to retrieve the first page of results for.
     * @return The first page of results.
     * @throws InvalidPagingRequestException if no previous page exists to retrieve.
     * @throws BuildkiteException if API returns an error response.
     */
    public <T> T firstPage(final PageableResponse<T> response) {
        // Validate
        Objects.requireNonNull(response);
        final PagingLinks pagingLinks = Objects.requireNonNull(response.getPagingLinks());
        if (!pagingLinks.hasFirstUrl()) {
            throw new InvalidPagingRequestException(
                "Requested 'First' page on response " + response.getClass().getSimpleName() + ", but no First page is available."
            );
        }

        // Update request with appropriate page options.
        final PageOptions pageOptions;
        try {
            pageOptions = PageOptions.fromUrl(pagingLinks.getNextUrl());
        } catch (final IllegalArgumentException ex) {
            throw new InvalidPagingRequestException("Unable to parse URL for paging information", ex);
        }
        final PageableRequest<T> request = response.getOriginalRequest();
        request.updatePageOptions(pageOptions);

        // Execute and return.
        return executeRequest(request);
    }

    /**
     * Retrieve the last page of results from the previously retrieved request.
     *
     * @param <T> The parsed return object representing the result.
     * @param response Previously retrieved result/response to retrieve the last page of results for.
     * @return The last page of results.
     * @throws InvalidPagingRequestException if no previous page exists to retrieve.
     * @throws BuildkiteException if API returns an error response.
     */
    public <T> T lastPage(final PageableResponse<T> response) {
        // Validate
        Objects.requireNonNull(response);
        final PagingLinks pagingLinks = Objects.requireNonNull(response.getPagingLinks());
        if (!pagingLinks.hasLastUrl()) {
            throw new InvalidPagingRequestException(
                "Requested 'Last' page on response " + response.getClass().getSimpleName() + ", but no Last page is available."
            );
        }

        // Update request with appropriate page options.
        final PageOptions pageOptions;
        try {
            pageOptions = PageOptions.fromUrl(pagingLinks.getNextUrl());
        } catch (final IllegalArgumentException ex) {
            throw new InvalidPagingRequestException("Unable to parse URL for paging information", ex);
        }
        final PageableRequest<T> request = response.getOriginalRequest();
        request.updatePageOptions(pageOptions);

        // Execute and return.
        return executeRequest(request);
    }

    /**
     * Execute the given request, returning the parsed response, or throwing the appropriate
     * exception if an error was returned from the API.
     *
     * This method scoped public to allow for user defined requests to be executed by the library
     * as an extension point.
     *
     * @param <T> The parsed response object.
     * @param request The request to execute.
     * @return The parsed response object.
     * @throws BuildkiteException if API returns an error response.
     */
    public <T> T executeRequest(final Request<T> request) throws BuildkiteException {
        final HttpResult result = httpClient.executeRequest(request);

        // Debug logging of the result.
        logger.trace("HttpResult: {}", result);

        // Handle Errors based on HttpCode.
        if (result.getStatus() != 200 && result.getStatus() != 201 && result.getStatus() != 204) {
            handleError(result);
        }

        // Success response code, parse response into object and return.
        return request.parseResponse(result);
    }

    /**
     * Handle error responses from the API by throwing the appropriate exception.
     * @param errorResult Error response from REST API.
     * @throws BuildkiteException relating to specific underlying API error.
     */
    private void handleError(final HttpResult errorResult) throws BuildkiteException {
        // Attempt to parse error response.
        String errorMessage = null;
        List<Error> errors = Collections.emptyList();
        try {
            final ErrorResponse errorResponse = new ErrorResponseParser().parseResponse(errorResult);
            errorMessage = errorResponse.getMessage();
            errors = errorResponse.getErrors();
        } catch (final IOException e) {
            errorMessage = errorResult.getContent();
        }
        if (errorMessage != null && errorMessage.trim().isEmpty()) {
            errorMessage = null;
        }

        switch (errorResult.getStatus()) {
            case 401:
                throw new InvalidAccessTokenException(
                    errorMessage == null ? "Invalid Access Token" : errorMessage
                );
            case 403:
                throw new InvalidAllowedIpAddressException(
                    errorMessage == null
                    ?
                        "API requested from an IP address not specifically allowed by your AccessToken. "
                        + "Check the 'Allowed IP Addresses' field on your Access Token"
                    : errorMessage
                );
            case 404:
                throw new NotFoundException(
                    errorMessage == null
                        ?
                        "The URL or Resource Request could not be found"
                        : errorMessage
                );
            case 422:
                String validationErrorMessage = (( errorMessage != null) ? errorMessage : "The submitted request was invalid. ");
                validationErrorMessage += "\n" + errors.stream()
                    .map((error) -> error.getField() + ": " + error.getCode())
                    .collect(Collectors.joining("\n"));
                throw new InvalidRequestException(validationErrorMessage, errors);
            default:
                throw new BuildkiteException(
                    errorMessage == null ? "Unknown/Unhandled Error HttpCode: " + errorResult.getStatus() : errorMessage
                );
        }
    }
}
