@patient-file-contact-named-by-patient
Feature: Patient File Contacts named by Patient

  Background:
    Given there is a program area named "Tyrant Virus"
    And there is a jurisdiction named "Racoon City"
    And I am logged into NBS
    And I can "View" any "ct_contact" for Tyrant Virus in Racoon City
    And I have a patient
    And the patient has the Legal name "Claire" "Redfield"
    And I have another patient
    And the patient has the Legal name "Jill" "Valentine"
    And the patient is a subject of an investigation for Tyrant Virus within Racoon City
    And the patient names the previous patient as a contact on the investigation

  Scenario: I can view Contacts named by a patient
    Given the contact record has a High priority
    And the contact record was created on 04/01/2018 at 19:23:29
    And the contact record has a T1 - Positive Test referral basis
    And the contact record was named on 02/05/2014
    And the contact record has a Confirmed Case disposition
    And the contact record has a Insufficient Info processing decision
    When I view the contacts named by the patient
    Then the patient file has the contact "Claire" "Redfield" named by the patient
    And the patient file has the contact record with a T1 - Positive Test referral basis
    And the patient file has the contact record with a "High" priority
    And the patient file has the contact record created on 04/01/2018 at 19:23:29
    And the patient file has the contact record named on 02/05/2014
    And the patient file has the contact record with a "Confirmed Case" disposition
    And the patient file has the contact record with a "Insufficient Info" processing decision

  Scenario: I can view Contacts named by a patient with associated investigations for a patient
    Given there is a program area named "T-JCCC203"
    And there is a jurisdiction named "Arklay Mountains"
    And the previous patient is a subject of an investigation for T-JCCC203 within Arklay Mountains
    And the contact record is associated with the investigation
    And I can "view" any "investigation" for T-JCCC203 in Arklay Mountains
    When I view the contacts named by the patient
    Then the patient file has the contact record associated with the investigation

  Scenario: I cannot retrieve Contacts named by a patient associated investigations for a patient without permissions
    Given there is a program area named "T-JCCC203"
    And there is a jurisdiction named "Arklay Mountains"
    And the previous patient is a subject of an investigation for T-JCCC203 within Arklay Mountains
    And the contact record is associated with the investigation
    When I view the contacts named by the patient
    Then the patient file has the contact record not associated with the investigation


  Scenario: I cannot retrieve Contact records named by a patient that haven't been named
    Given I have another patient
    When I view the contacts named by the patient
    Then no values are returned
