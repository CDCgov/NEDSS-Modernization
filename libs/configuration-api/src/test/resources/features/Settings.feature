Feature: Frontend Settings Configuration

  Scenario: Property based Configuration values are available through the API
    When I request the frontend configuration
    Then the settings include a Smarty key of "smarty-key"
    And the settings include an analytics host of "analytics-host"
    And the settings include an analytics key of "analytics-key"
