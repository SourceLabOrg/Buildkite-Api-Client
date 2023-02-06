# Buildkite-Api-Client Java REST Api Client

## What is it?

This library provides an easy to use client library for interacting with the [Buildkite Rest API](https://buildkite.com/docs/apis/rest-api) (V2).

## How to use this library

This client library is released on Maven Central.  Add a new dependency to your project's POM file:

```xml
<dependency>
    <groupId>org.sourcelab</groupId>
    <artifactId>buildkite-api-client</artifactId>
    <version>0.1.1</version>
</dependency>
```


#### Example Code:
```java
/*
 * Create a new configuration object passing your API token.
 * Your API Token can be found/created at: https://buildkite.com/user/api-access-tokens
 */
final Configuration configuration = Configuration.newBuilder()
    .withApiToken("your-api-token-here")
    .build();

/*
 * Create an instance of BuildkiteClient, passing your configuration.
 */
final BuildkiteClient client = new BuildkiteClient(configuration);
    
/*
 * Making requests by calling the public methods available on BuildkiteClient.
 * 
 * For example, to retrieve information about your own user:
 * https://buildkite.com/docs/apis/rest-api/user
 */
final CurrentUserResponse myUser = client.getUser();

/*
 * To retrieve your organizations:
 * https://buildkite.com/docs/apis/rest-api/organizations#list-organizations
 */
final ListOrganizationsResponse organizations = client.listOrganizations();

/*
 * To search for builds:  
 * https://buildkite.com/docs/apis/rest-api/builds#list-all-builds   
 */
 */
final BuildFilters filters = BuildFilters.newBuilder()
    // Retrieve 1st page, with each page having 25 results
    .withPageOptions(1, 25)
    // Filtering for builds with state 'failed'
    .withStateChooser().failed()
    // And created within the last day
    .withCreatedFrom(ZonedDateTime.now().minusDays(1))
    .build();
final ListBuildsResponse buildsResult = client.listBuilds(filters);

/*
 * To retrieve the 2nd page of the above query simply do:   
 */
if (buildsResult.hasNextPage()) {
    final ListBuildsResponse buildsPage2 = client.nextPage(buildsResult);
}

/*
 * See BuildkiteClient for other available operations and end points.
 */
```

Public methods available on BuildkiteClient can be [found here](src/main/java/org/sourcelab/buildkite/api/client/BuildkiteClient.java#L111)

## Endpoints Supported
### Pipelines API
- [Access Token](https://buildkite.com/docs/apis/rest-api/access-token)
  - [Get the current token](https://buildkite.com/docs/apis/rest-api/access-token#get-the-current-token).
  - [Revoke the current token](https://buildkite.com/docs/apis/rest-api/access-token#revoke-the-current-token).
- [Organizations](https://buildkite.com/docs/apis/rest-api/organizations)
  - [List organizations](https://buildkite.com/docs/apis/rest-api/organizations#list-organizations)
  - [Get an organization](https://buildkite.com/docs/apis/rest-api/organizations#get-an-organization)
- [Pipelines](https://buildkite.com/docs/apis/rest-api/pipelines)
  - [List pipelines](https://buildkite.com/docs/apis/rest-api/pipelines#list-pipelines)
  - [Get a pipeline](https://buildkite.com/docs/apis/rest-api/pipelines#get-a-pipeline)
- [Builds](https://buildkite.com/docs/apis/rest-api/builds)
  - [List all builds](https://buildkite.com/docs/apis/rest-api/builds#list-all-builds)
  - [List builds for an organization](https://buildkite.com/docs/apis/rest-api/builds#list-builds-for-an-organization)
  - [List builds for a pipeline](https://buildkite.com/docs/apis/rest-api/builds#list-builds-for-a-pipeline)
  - [Get a build](https://buildkite.com/docs/apis/rest-api/builds#get-a-build)
  - [Create a build](https://buildkite.com/docs/apis/rest-api/builds#create-a-build)
  - [Cancel a build](https://buildkite.com/docs/apis/rest-api/builds#cancel-a-build)
  - [Rebuild a build](https://buildkite.com/docs/apis/rest-api/builds#rebuild-a-build)
- [Jobs](https://buildkite.com/docs/apis/rest-api/jobs)
  - [Retry a job](https://buildkite.com/docs/apis/rest-api/jobs#retry-a-job)
- [Emojis](https://buildkite.com/docs/apis/rest-api/emojis)
  - [List emojis](https://buildkite.com/docs/apis/rest-api/emojis#list-emojis)
- [User](https://buildkite.com/docs/apis/rest-api/user)
  - [Get the current user](https://buildkite.com/docs/apis/rest-api/user#get-the-current-user)

**Not all endpoints implemented yet**

It should be noted that not all REST endpoints are currently implemented. If you require an end point not yet supported, either submit an issue with a feature request, or submit a pull request implementing it.

The functionality that exists in the library should be considered stable, and every attempt will be made to avoid breaking backwards
compatibility as the library is expanded. 

# Contributing

Found a bug? Think you've got an awesome feature you want to add? We welcome contributions!


## Submitting a Contribution

1. Search for an existing issue. If none exists, create a new issue so that other contributors can keep track of what you are trying to add/fix and offer suggestions (or let you know if there is already an effort in progress).  Be sure to clearly state the problem you are trying to solve and an explanation of why you want to use the strategy you're proposing to solve it.
1. Fork this repository on GitHub and create a branch for your feature.
1. Clone your fork and branch to your local machine.
1. Commit changes to your branch.
1. Push your work up to GitHub.
1. Submit a pull request so that we can review your changes.

*Make sure that you rebase your branch off of master before opening a new pull request. We might also ask you to rebase it if master changes after you open your pull request.*

## Acceptance Criteria

We love contributions, but it's important that your pull request adhere to some of the standards we maintain in this repository.

- All tests must be passing!
- All code changes require tests!
- All code changes must be consistent with our checkstyle rules.
- Great inline comments.

# Other Notes

## Releasing

Steps for proper release:
- Update release version: `mvn versions:set -DnewVersion=X.Y.Z`
- Validate and then commit version: `mvn versions:commit`
- Update CHANGELOG and README files.
- Merge to master.
- Deploy to Maven Central: `mvn clean deploy -P release`
- Create release on Github project.


## Changelog

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

[View Changelog](CHANGELOG.md)



