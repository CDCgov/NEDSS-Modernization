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

| Name                               | Default                  | Description                                                                                                                                                                                                                                                                                     |
|------------------------------------|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| nbs.ui.settings.smarty.key         |                          | The embedded API key, when blank the Smarty API will not be invoked.                                                                                                                                                                                                                            |
| nbs.ui.settings.analytics.host     | https://us.i.posthog.com | The host name of the PostHog server to send analytics to.                                                                                                                                                                                                                                       |
| nbs.ui.settings.analytics.key      |                          | The PostHog project key to associate frontend analytics with, when blank analytics will not be enabled.                                                                                                                                                                                         |
| nbs.ui.settings.defaults.sizing    | medium                   | The default sizing of components on the modernized user interface.                                                                                                                                                                                                                              |
| nbs.ui.settings.defaults.country   | 840 (United States)      | The default country for addresses.                                                                                                                                                                                                                                                              |
| nbs.ui.settings.session.warning    | 28m                      | A [Duration](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.conversion.durations) that defines the amount of time to wait while the user is idle before warning of session timeout                       |
| nbs.ui.settings.session.expiration | 30m                      | A [Duration](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.conversion.durations) that defines the amount of time to wait while the user is idle before navigating the user to the expired session page. |

_Note:  The `session` configuration settings are meant for a user interface to be able to alert a user that a session is
going to expire and do not actually control the session timeout. The `warning`, and `expiration` settings should
correspond to the session expiration configuration on the OIDC provider.

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
