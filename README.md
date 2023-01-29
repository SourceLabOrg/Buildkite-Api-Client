# Buildkite-Api-Client Java REST Api Client

## What is it?

This library provides an easy to use client library for interacting with the [Buildkite Rest API](https://buildkite.com/docs/apis/rest-api) (V2).

## How to use this library

This client library is released on Maven Central.  Add a new dependency to your project's POM file:

```xml
<dependency>
    <groupId>org.sourcelab</groupId>
    <artifactId>buildkite-api-client</artifactId>
    <version>0.1.0</version>
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
 * To retrieve the 3rd page of the above query simply do:   
 */
if (buildsResult.hasNextPage()) {
    final ListBuildsResponse buildsPage2 = client.nextPage(buildsPage1);
}

/*
 * See BuildkiteClient for other available operations and end points.
 */
```

Public methods available on BuildkiteClient can be [found here](src/main/java/org/sourcelab/buildkite/api/client/BuildkiteClient.java#L111)

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



