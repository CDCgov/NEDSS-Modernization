@patient-profile-general-information-change
Feature: Patient Profile General Information Changes

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I can "edit" any "patient"
    And I have a patient
    And the patient is associated with state HIV case "case-number"

  Scenario: I can view the state HIV case of a patient when I have access to HIV Fields
    Given I can "HIVQuestions" any "Global"
    When I view the patient's general information
    Then the patient's general information includes a "state HIV case" of "case-number"

  Scenario: I can not view the state HIV case of a patient without access to HIV Fields
    When I view the patient's general information
    Then the patient's general information does not include state HIV case due to "insufficient permissions"

  Scenario: I can update a patient's general information to include the state HIV case a patient may be associated with when I have access to HIV Fields
    Given I can "HIVQuestions" any "Global"
    And I want to change the patient's general information to include that the patient is associated with state HIV case "changed case-number"
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes a "state HIV case" of "changed case-number"

  Scenario: I can not update a patient's general information to include the state HIV case a patient may be associated with without access to HIV Fields
    Given I want to change the patient's general information to include that the patient is associated with state HIV case "changed case-number"
    When the patient profile general information changes are submitted as of 04/17/2013
    And I can "HIVQuestions" any "Global"
    And I view the patient's general information
    Then the patient's general information includes a "state HIV case" of "case-number"


