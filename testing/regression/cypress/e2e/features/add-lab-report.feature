Feature: Add Laboratory Report

  Background:
    Given I am logged in as "superuser" and password ""
    
  Scenario: User adds a new lab report
    Then I create a new patient for E2E lab report
    Then user clicks on a patient's profile "Events" tab
    Then user creates lab report and investigation for new patient