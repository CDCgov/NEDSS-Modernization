@patient-file-header
Feature: Patient File Header

  Scenario: I can retrieve the patient file header for a patient
    Given I am logged into NBS
    And I have a patient
    And the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a Case Report
    When I view the Patient File Header
    Then the patient status is "ACTIVE"
    Then the patient deletability is "Has_Associations"

  Scenario: I get a blank body when no name is found
    Given I am logged into NBS
    And I have a patient
    And the patient has a Case Report
    When I view the Patient File Header
    Then the packet is blank

  Scenario: I get a deletable patient when no events are associated
    Given I am logged into NBS
    And I have a patient
    And the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000
    When I view the Patient File Header
    Then the patient deletability is "Deletable"

  Scenario: I get a blank body when no events or names
    Given I am logged into NBS
    And I have a patient
    When I view the Patient File Header
    Then the packet is blank

  Scenario: I get a blank body when no patient
    Given I am logged into NBS
    When I view the Patient File Header
    Then the packet is blank

  Scenario: I get inactive if the patient is inactive
    Given I am logged into NBS
    And I have a patient
    And the patient is inactive
    And the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a Case Report
    When I view the Patient File Header
    Then the patient status is "INACTIVE"
    Then the patient deletability is "Is_Inactive"
