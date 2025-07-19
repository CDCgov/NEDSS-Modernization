@patient-edit
Feature: Editing of Patient General Information

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

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
    When I edit a patient with entered demographics
    Then I view the patient's general information demographics
    And the patient file general information is as of 05/29/2023
    And the patient file general information includes the marital status Unmarried
    And the patient file general information includes a mother's maiden name of "Zawistowska"
    And the patient file general information includes 7 adults in the residence
    And the patient file general information includes 3 children in the residence
    And the patient file general information includes the primary occupation Industrial Gas Manufacturing
    And the patient file general information includes an education level of Professional Degree
    And the patient file general information includes a primary language of Polish
    And the patient file general information includes that the patient does not speak English
    And the patient history contains the previous version

  Scenario: I can edit a patient with general information demographics that include a State HIV case when I have access to HIV fields
    Given I can "HIVQuestions" any "Global"
    And I enter the general information state HIV case of "case-number"
    When I edit a patient with entered demographics
    And I view the patient's general information demographics
    Then the patient file general information includes a state HIV case of "case-number"
    And the patient history contains the previous version

  Scenario: I can edit a patient with general information demographics that does not include a blank State HIV case
    Given I can "HIVQuestions" any "Global"
    And I enter the general information state HIV case of ""
    When I edit a patient with entered demographics
    And I view the patient's general information demographics
    Then the patient file general information does not include a state HIV case
    And the patient history contains the previous version

  Scenario: I can edit a patient's general information but it will not include the state HIV case a patient may be associated with without having access to HIV Fields
    Given I enter the general information state HIV case of "case-number"
    When I edit a patient with entered demographics
    And I can "HIVQuestions" any "Global"
    Then I view the patient's general information demographics
    And the patient file general information does not include a state HIV case
    And the patient history contains the previous version
