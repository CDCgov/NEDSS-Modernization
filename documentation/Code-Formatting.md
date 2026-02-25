# Code Formatting

## Java Formatting

Java Code is formatted using the Google Java Style Guide. We use spotless to check and fix formatting on files changed by the branch. It will run on build and CI will check that it's been run. 

We recommend adding the [pre-push hook](https://github.com/diffplug/spotless/blob/e36deaf0c763f5bda67126a6cb3344e1baaabfe5/plugin-gradle/README.md#git-hook) to check formatting before pushing to GitHub.

```sh
./gradlew spotlessInstallGitPrePushHook
```

### Running Formatting

To check if the code is formatted correctly:
```sh
./gradlew spotlessCheck
```

To fix the code formatting (also runs on build):
```sh
./gradlew spotlessApply
```

### Editor Setup

While the spotless formatting is the source of truth for what is correct. You can set up your editor to directly use the [google-java-format](https://github.com/google/google-java-format?tab=readme-ov-file#third-party-integrations) so functions like format on save are available.
