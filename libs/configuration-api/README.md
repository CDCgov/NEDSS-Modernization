# Configuration-api

A library that provides the `/nbs/api/configuration` endpoint to serve configuration to
the [modernized-ui](/apps/modernization-ui/). Spring Config allows configuration values to be overwritten at deployment.
Values can be set through Java System Variables, Environment Variable,
and [other useful means](https://docs.spring.io/spring-boot/reference/features/external-config.html).

Configuration properties can be overwritten at runtime using the `--args` Gradle option to pass arguments to Spring
Boot.

## Settings

The `settings` property in the `/nbs/api/configuration` response contains UI and session configuration values.

| Name                               | Default                    | Description                                                                                                                                                                                                                                                                                     |
| ---------------------------------- | -------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| nbs.ui.settings.smarty.key         | _(blank)_                  | The embedded API key, when blank the Smarty API will not be invoked.                                                                                                                                                                                                                            |
| nbs.ui.settings.analytics.host     | `https://us.i.posthog.com` | The host name of the PostHog server to send analytics to.                                                                                                                                                                                                                                       |
| nbs.ui.settings.analytics.key      | _(blank)_                  | The PostHog project key to associate frontend analytics with, when blank analytics will not be enabled.                                                                                                                                                                                         |
| nbs.ui.settings.defaults.sizing    | `medium`                   | The default sizing of components on the modernized user interface.                                                                                                                                                                                                                              |
| nbs.ui.settings.defaults.country   | `840` (United States)      | The default country for addresses.                                                                                                                                                                                                                                                              |
| nbs.ui.settings.session.warning    | `28m`                      | A [Duration](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.conversion.durations) that defines the amount of time to wait while the user is idle before warning of session timeout                       |
| nbs.ui.settings.session.expiration | `30m`                      | A [Duration](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.conversion.durations) that defines the amount of time to wait while the user is idle before navigating the user to the expired session page. |

> **Note:**  
> The `session` settings are intended for UI alerts and do not control actual session timeouts. Ensure these values match the session expiration configuration of your OIDC provider.

## Features

The `features` property in the `/nbs/api/configuration` response contains [feature flags](/documentation/feature-flags/README.md) for enabling or disabling UI functionality.

| Name                                                | Default | Description                                                        |
| --------------------------------------------------- | ------- | ------------------------------------------------------------------ |
| nbs.ui.features.address.autocomplete                | false   | Enables the address autocomplete feature                           |
| nbs.ui.features.address.verification                | false   | Enables the address verification feature                           |
| nbs.ui.features.pageBuilder.enabled                 | false   | Enables the PageBuilder feature                                    |
| nbs.ui.features.pageBuilder.page.library.enabled    | false   | Enables the PageBuilder Page Library feature                       |
| nbs.ui.features.pageBuilder.page.management.enabled | false   | Enables the PageBuilder Page Management feature                    |
| nbs.ui.features.search.events.enabled               | true    | Enables access to NBS6 Event Search                                |
| nbs.ui.features.search.investigations.enabled       | false   | Enables access to modernized Investigation search                  |
| nbs.ui.features.search.laboratoryReports.enabled    | false   | Enables access to modernized Laboratory search                     |
| nbs.ui.features.patient.search.filters.enabled      | false   | Enables access to modernized Patient search filters                |
| nbs.ui.features.patient.file.enabled                | false   | Enables access to modernized Patient file                          |
| nbs.ui.features.patient.file.merge-history.enabled  | false   | Enables access to merge history within the modernized Patient file |
| nbs.ui.features.report.execution.enabled            | false   | Enables access to report execution and manaement                   |

---

## Additional Information

- See [Spring Boot External Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html) for details on overriding configuration values.
- For feature flag documentation, refer to [feature-flags/README.md](/documentation/feature-flags/README.md).
- This library is intended for use with the modernized NBS UI.
