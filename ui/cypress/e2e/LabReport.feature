Feature: Lab Report

  Scenario: I can create a lab report
    Given I am a data entry user
    And a patient exists
    When I create a lab report
    Then I can view the report in the Documents Requiring Review queue
