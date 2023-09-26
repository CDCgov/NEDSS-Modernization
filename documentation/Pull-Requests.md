# Pull Request Guidelines

## Creating a Pull Request

To merge a branch into `main`, a pull request must first be created. All pull requests should follow the provided [template](../.github/pull_request_template.md). A pull requests should correspond to a single Jira ticket except in the case of duplicate tickets. The smaller the pull request the better.

Prior to creating a pull request:

1. Confirm acceptance criteria in the Jira issue have been met
1. All tests have been executed locally
   - All tests should pass
   - The [Jacoco report](../build/reports/jacoco/codeCoverageReport/html/index.html) should show proper test coverage

It is recommended to create all new pull requests as a `Draft` and perform a self review prior to notifying a reviewer. Once the self review is complete, verify the sonar scans completed successfully. At this point the PR should be marked as `ready for review` and the appropriate reviewer be notified.

## Reviewing a Pull Request

When a pull request is marked as `ready for review` and you are marked as a reviewer:

1. Verify the sonar scans
1. Verify the code coverage
1. **Run the code** and validate that the Jira issue acceptance criteria has been met
1. Look for optimizations

If changes are requested, be precise and provide explanation.

Once the pull request has been reviewed and approved. The `creator` of the pull request should be responsible for `quashing and merging` the pull request.
