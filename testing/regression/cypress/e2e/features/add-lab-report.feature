Feature: Add Laboratory Report

  Background:
    Given I am logged in as "superuser" and password ""
    
  Scenario: User adds a new lab report
    Given the user navigate to the patient profile page for "95066"
    Then user clicks on a patient's profile "Events" tab
    Then user creates lab report and investigation for patient id "95066"