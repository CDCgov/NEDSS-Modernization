@patient-file @patient-demographics-summary
Feature: Viewing the administrative information of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario:  I cannot view the administrative information of a patient that does not exist
    When I view the Administrative information for patient 1039
    Then it was not found

  Scenario: I can view the patient's administrative information
    Given the patient has an administrative as of date of 09/13/2013
    And the patient has an administrative comment of "some comment value"
    When I view the patient's Administrative information
    And the patient file administrative information has the as of date 09/13/2013
    And the patient file administrative information has the comment "some comment value"
