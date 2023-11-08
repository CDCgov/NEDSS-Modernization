@patient-profile-documents @documents
Feature: Patient Profile Documents

  Background:
    Given I am logged in
    And I can "FIND" any "PATIENT"
    And I have a patient

  Scenario: I cannot retrieve documents without proper authorities
   Then the profile documents are not returned


