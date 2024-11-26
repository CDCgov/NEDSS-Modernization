# Pull Request Guidelines

## Creating a Pull Request

To merge a branch into `main`, a pull request must first be created. All pull requests should follow the provided [template](../.github/pull_request_template.md) and have the corresponding Jira ticket number in the title.

A pull requests should correspond to a single Jira ticket, except in the case of duplicate tickets. The smaller the pull request the better.

**Prior to creating a pull request:**

1. Verify acceptance criteria in the Jira issue have been met
1. Verify code will build and run without errors
1. Run all tests locally
   - To verify frontend tests run `npm run test - --watchAll=false` from the `apps/modernization-ui` folder
   - To verify backend tests, run `./gradlew test` from the root folder
1. Verify all tests pass
1. Verify the [Jacoco report](../build/reports/jacoco/codeCoverageReport/html/index.html) shows proper test coverage
1. Verify all checks (CI/CD) have passed (Green checkmark) on a Draft PR, before marking it "ready for review"

NOTE: It is recommended to create all new pull requests as a `Draft` and perform a self review prior to notifying a reviewer. Once the self review is complete, verify the sonar scans have completed successfully. At this point the PR should be marked as `ready for review` and the appropriate reviewer notified.

## Reviewing a Pull Request

When a pull request is marked as `ready for review` and you are marked as a reviewer:

1. Verify all checks (CI/CD) have passed (Green checkmark)
1. **Run the code** and validate that the Jira issue acceptance criteria has been met
1. Look for optimizations

If changes are requested, be precise and provide explanation.


Once the pull request has been reviewed and approved. The `creator` of the pull request should be responsible for `squashing and merging` the pull request.
