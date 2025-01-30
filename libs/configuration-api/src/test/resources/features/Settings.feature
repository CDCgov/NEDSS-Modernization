Feature: Frontend Settings Configuration

  Scenario Outline: Property based Configuration values are available through the API
    Given I set the <setting> setting to "<value>"
    When I request the frontend configuration
    Then the settings include a <setting> of "<value>"

    Examples:
      | setting         | value           |
      | Smarty key      | smarty-key      |
      | analytics host  | analytics-host  |
      | analytics key   | analytics-key   |
      | default sizing  | default-sizing  |
      | default country | default-country |
