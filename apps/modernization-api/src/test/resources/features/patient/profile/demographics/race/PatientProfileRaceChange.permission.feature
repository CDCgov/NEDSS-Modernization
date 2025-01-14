@patient-profile-race @patient-profile-race-change @web-interaction
Feature: Patient Profile Race Change Permissions

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I have a patient

  Scenario: I cannot add a patient's race without proper permission
    When I add a patient's race as of 01/17/2023
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot update a patient's race without proper permission
    Given the patient has the race category Asian
    When I update a patient's race category of Asian as of 04/17/2020
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot remove a patient's race without proper permission
    When I remove a patient's race
    Then I am not allowed due to insufficient permissions
