Feature: Frontend Feature Configuration

  Scenario: Features enabled by defaults
    When I request the frontend configuration
    Then the search view feature is enabled
    And the tabular search results feature is enabled
    And the patient search filters feature is disabled
    And the patient add feature is disabled
    And the patient add extended feature is disabled
    And the NBS6 event search feature is enabled
    And the investigation search feature is disabled
    And the laboratory report search feature is disabled

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
      | patient search filters     | disabled  |
      | patient add                | enabled  |
      | patient add                | disabled |
      | patient add extended       | enabled  |
      | patient add extended       | disabled |
      | NBS6 event search          | enabled  |
      | NBS6 event search          | disabled |
      | investigation search       | enabled  |
      | investigation search       | disabled |
      | laboratory report search   | enabled  |
      | laboratory report search   | disabled |
      | modernized patient profile | enabled  |
      | modernized patient profile | disabled |
