@patient-file @patient-general-demographics
Feature: Viewing the sensitive general demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can view a Patient's state HIV case when I have access to HIV Fields
    Given I can "HIVQuestions" any "Global"
    And the patient is associated with state HIV case "case-number"
    When I view the patient's general information demographics
    Then the patient file general information includes a state HIV case of "case-number"

  Scenario: I can not view a Patient's state HIV case when I do not have access to HIV Fields
    Given the patient is associated with state HIV case "case-number"
    When I view the patient's general information demographics
    Then the patient file general information does not include state HIV case due to "insufficient permissions"
