@investigation_search
Feature: Investigation search permission

  Scenario Outline: I cannot search for Investigations without required permissions
    Given I am logged into NBS
    And I can "<operation>" any "<object>"
    When I search for investigations
    Then the Investigation search results are not accessible

    Examples:
      | operation | object               |
      | find      | patient              |
      | view      | Investigation |

  Scenario: I cannot search for Investigations without logging in
    Given I am not logged in at all
    When I search for investigations
    Then the Investigation search results are not accessible
