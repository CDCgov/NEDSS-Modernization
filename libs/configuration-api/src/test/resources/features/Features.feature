Feature: Frontend Feature Configuration

  Scenario: Property based Frontend Feature Configuration is available through the API
    When I request the frontend configuration
    Then the search view feature is enabled
    And the search table view feature is disabled
