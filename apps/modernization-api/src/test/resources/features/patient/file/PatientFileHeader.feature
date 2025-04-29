@patient-file-header
Feature: Patient File Header

  Background:
    Given I have a patient

  Scenario: I can retrieve the patient file header for a patient
    Given I am logged into NBS
    Then I view the Patient File Header
