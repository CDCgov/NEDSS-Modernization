@frontend-features
Feature: Frontend Feature Configuration

  Scenario: Features enabled by defaults
    When I request the frontend configuration
    Then the search view feature is enabled
    And the tabular search results feature is enabled
    And the patient search filters feature is disabled
    And the NBS6 event search feature is enabled
    And the investigation search feature is disabled
    And the laboratory report search feature is disabled
    And the deduplication feature is disabled
    And the patient file feature is disabled
    And the patient file merge history feature is disabled
    And the system management feature is disabled

  Scenario Outline: I can configure Frontend features
    Given I <toggle> the <feature> feature
    When I request the frontend configuration
    Then the <feature> feature is <toggle>

    Examples:
      | feature                    | toggle   |
      | search view                | enabled  |
      | search view                | disabled |
      | tabular search results     | enabled  |
      | tabular search results     | disabled |
      | patient search filters     | enabled  |
      | patient search filters     | disabled |
      | NBS6 event search          | enabled  |
      | NBS6 event search          | disabled |
      | investigation search       | enabled  |
      | investigation search       | disabled |
      | laboratory report search   | enabled  |
      | laboratory report search   | disabled |
      | modernized patient profile | enabled  |
      | modernized patient profile | disabled |
      | deduplication              | disabled |
      | deduplication              | enabled  |
      | deduplication merge        | disabled |
      | deduplication merge        | enabled  |
      | patient file               | enabled  |
      | patient file               | disabled |
      | patient file merge history | enabled  |
      | patient file merge history | disabled |
      | system management          | enabled  |
      | system management          | disabled |
