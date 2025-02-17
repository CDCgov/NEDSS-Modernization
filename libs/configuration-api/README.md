# Configuration-api

A library that provides the `/nbs/api/configuration` endpoint to serve configuration to
the [modernized-ui](/apps/modernization-ui/). Spring Config allows configuration values to be overwritten at deployment.
Values can be set through Java System Variables, Environment Variable,
and [other useful means](https://docs.spring.io/spring-boot/reference/features/external-config.html).

Configuration properties can be overwritten at runtime using the `--args` Gradle option to pass arguments to Spring
Boot.

## Settings

Provides the values Settings returned with in the `settings` property of
the response of the `/nbs/api/configuration` endpoint.

| Name                             | Default                  | Description                                                                                             |
|----------------------------------|--------------------------|---------------------------------------------------------------------------------------------------------|
| nbs.ui.settings.smarty.key       |                          | The embedded API key, when blank the Smarty API will not be invoked.                                    |
| nbs.ui.settings.analytics.host   | https://us.i.posthog.com | The host name of the PostHog server to send analytics to.                                               |
| nbs.ui.settings.analytics.key    |                          | The PostHog project key to associate frontend analytics with, when blank analytics will not be enabled. |
| nbs.ui.settings.defaults.sizing  | medium                   | The default sizing of components on the modernized user interface.                                      |
| nbs.ui.settings.defaults.country | 840 (United States)      | The default country for addresses.                                                                      |

## Features

Provides the values [Feature flags](/documentation/feature-flags/README.md) returned with in the `features` property of
the response of the `/nbs/api/configuration` endpoint.

| Name                                                | Default | Description                                         |
|-----------------------------------------------------|---------|-----------------------------------------------------|
| nbs.ui.features.address.autocomplete                | false   | Enables the address autocomplete feature            |
| nbs.ui.features.address.verification                | false   | Enables the address verification feature            |
| nbs.ui.features.pageBuilder.enabled                 | false   | Enables the PageBuilder feature                     |
| nbs.ui.features.pageBuilder.page.library.enabled    | false   | Enables the PageBuilder Page Library feature        |
| nbs.ui.features.pageBuilder.page.management.enabled | false   | Enables the PageBuilder Page Management feature     |
| nbs.ui.features.search.events.enabled               | true    | Enables access to NBS6 Event Search                 |
| nbs.ui.features.search.investigations.enabled       | false   | Enables access to modernized Investigation search   |
| nbs.ui.features.search.laboratoryReports.enabled    | false   | Enables access to modernized Laboratory search      |
| nbs.ui.features.patient.search.filters.enabled      | false   | Enables access to modernized Patient search filters |
| nbs.ui.features.patient.add.enabled                 | false   | Enables access to modernized Patient short form add |
| nbs.ui.features.patient.add.extended.enabled        | false   | Enables access to modernized Patient Extended add   |
| nbs.ui.features.patient.profile.enabled             | false   | Enables access to modernized Patient Profile        |
