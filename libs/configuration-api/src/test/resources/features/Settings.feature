Feature: Frontend Settings Configuration

  Scenario Outline: Default values are defined for Settings
    When I request the frontend configuration
    Then the settings include a <setting> of "<value>"

    Examples:
      | setting            | value                    |
      | analytics host     | https://us.i.posthog.com |
      | default sizing     | medium                   |
      | default country    | 840                      |
      | session warning    | 1680000                  |
      | session expiration | 1800000                  |


  Scenario Outline: Property based Configuration values are available through the API
    Given I set the <setting> setting to "<value>"
    When I request the frontend configuration
    Then the settings include a <setting> of "<value>"

    Examples:
      | setting            | value           |
      | Smarty key         | smarty-key      |
      | analytics host     | analytics-host  |
      | analytics key      | analytics-key   |
      | default sizing     | default-sizing  |
      | default country    | default-country |
      | session warning    | 1063            |
      | session expiration | 1049            |

