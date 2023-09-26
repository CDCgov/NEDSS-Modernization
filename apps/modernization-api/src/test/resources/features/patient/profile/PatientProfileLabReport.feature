@patient-profile-lab-reports @web-interaction
Feature: Patient Profile Lab Reports

  Background:
    Given I have a patient

  Scenario: A lab report is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a lab Report
    When the lab report is viewed from the Patient Profile
    Then the classic profile is prepared to view a lab report
    And I am redirected to Classic NBS to view a lab report

  Scenario: A lab report is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a lab Report
    When the lab report is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS lab report

  Scenario: A lab report is added from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "ADD-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"
    When a lab report is added from a Patient Profile
    Then the classic profile is prepared to add a lab report
    And I am redirected to Classic NBS to add a lab report

  Scenario: A lab report is added from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    When a lab report is added from a Patient Profile
    Then I am not allowed to add a Classic NBS lab report

  Scenario: A lab report is submitted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When a lab report is submitted from Classic NBS
    Then the lab report is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile
