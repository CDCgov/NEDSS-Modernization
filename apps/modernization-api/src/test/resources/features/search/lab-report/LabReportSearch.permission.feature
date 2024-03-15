@lab_report_search
Feature: Lab report search permission

  Scenario Outline: I cannot search for Lab Reports without required permissions
    Given I am logged into NBS
    And I can "<operation>" any "<object>"
    When I search for lab reports
    Then the Lab Report search results are not accessible

    Examples:
      | operation | object               |
      | find      | patient              |
      | view      | ObservationLabReport |

  Scenario: I cannot search for Lab Reports without logging in
    Given I am not logged in at all
    When I search for lab reports
    Then the Lab Report search results are not accessible
