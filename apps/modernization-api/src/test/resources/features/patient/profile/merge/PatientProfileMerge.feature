@patient-profile-merge-investigation @web-interaction
Feature: Patient Profile Investigation Merge

  Background:
    Given I have a patient

  Scenario: An investigation is deleted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When an investigation is merged from Classic NBS
    Then the investigation merge is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile
