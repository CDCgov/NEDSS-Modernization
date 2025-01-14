@patient_extended_create
Feature: Creation of Patients with extended General Information data

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "find" any "patient"

  Scenario: I can create a patient with general information demographics
    Given I enter the general information as of date 05/29/2023
    And I enter the general information marital status as Unmarried
    And I enter the general information maternal maiden name as "Zawistowska"
    And I enter the general information adults in residence as 7
    And I enter the general information children in residence as 3
    And I enter the general information primary occupation of Industrial Gas Manufacturing
    And I enter the general information education level of Professional Degree
    And I enter the general information primary language of Polish
    And I enter the general information that the patient does not speak english
    And the general information demographics are included in the extended patient data
    When I create a patient with extended data
    Then I view the Patient Profile General Information
    And the patient's general information is as of 05/29/2023
    And the patient's general information includes the marital status Unmarried
    And the patient's general information includes a mother's maiden name of "Zawistowska"
    And the patient's general information includes 7 adults in the house
    And the patient's general information includes 3 children in the house
    And the patient's general information includes the occupation Industrial Gas Manufacturing
    And the patient's general information includes an education level of Professional Degree
    And the patient's general information includes a primary language of Polish
    And the patient's general information includes that the patient does not speak English

  Scenario: I can add a patient with general information demographics that include a State HIV case when I have access to HIV fields
    Given I can "HIVQuestions" any "Global"
    And I enter the general information state HIV case of "case-number"
    And the general information demographics are included in the extended patient data
    When I create a patient with extended data
    Then I view the Patient Profile General Information
    Then the patient's general information includes a state HIV case of "case-number"

  Scenario: I can add a patient with general information demographics that does not include a blank State HIV case
    Given I can "HIVQuestions" any "Global"
    And I enter the general information state HIV case of ""
    And the general information demographics are included in the extended patient data
    When I create a patient with extended data
    Then I view the Patient Profile General Information
    And the patient's general information does not include a state HIV case

  Scenario: I can add a patient's general information but it will not include the state HIV case a patient may be associated with without having access to HIV Fields
    Given I enter the general information state HIV case of "case-number"
    And the general information demographics are included in the extended patient data
    When I create a patient with extended data
    And I can "HIVQuestions" any "Global"
    Then I view the Patient Profile General Information
    And the patient's general information does not include a state HIV case
