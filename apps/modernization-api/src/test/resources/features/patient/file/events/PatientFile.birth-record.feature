@patient-file-birth-record
Feature: Patient File Birth records

  Background:
    Given I am logged in
    And I can "view" any "BirthRecord"
    And I have a patient

  Scenario: I cannot retrieve Birth records for a patient that has no birth records
    When I view the birth records for the patient
    Then no values are returned

  Scenario: I can retrieve Birth records for a patient
    Given there is an organization named "a place beyond this world"
    And the patient has the "K9999" birth record
    And the birth record was received on 04/25/2010 at 11:15:17
    And the birth record was collected on 12/20/1982
    And the birth record has the patient born at a place beyond this world
    When I view the birth records for the patient
    Then the patient file has the birth record for the "K9999" certificate
    And the patient file has the birth record received on 04/25/2010 at 11:15:17
    And the patient file has the birth record collected on 12/20/1982
    And the patient file has the birth record born at "a place beyond this world"
    And the patient file has the birth record not associated with any investigations

  Scenario: I can retrieve Birth records for a patient with the Mother's name
    Given the patient has a birth record
    And the birth record has the mother named "Margaret" "Murdock"
    When I view the birth records for the patient
    Then the patient file has the birth record includes the mother's first name "Margaret"
    And the patient file has the birth record includes the mother's last name "Murdock"

  Scenario: I can retrieve Birth records for a patient with the Mother's full name
    Given the patient has a birth record
    And the birth record has the mother named "Mary" "Katherine" "Gallagher" Jr.
    When I view the birth records for the patient
    Then the patient file has the birth record includes the mother's first name "Mary"
    And the patient file has the birth record includes the mother's middle name "Katherine"
    And the patient file has the birth record includes the mother's last name "Gallagher"
    And the patient file has the birth record includes the mother's name with the suffix "Jr."

  Scenario: I can retrieve Birth records for a patient with the Mother's address
    Given the patient has a birth record
    And the birth record has the mother living at "2878 Wescam Court" "Apt 227" "Saugus" Massachusetts "01906" in Essex County
    When I view the birth records for the patient
    Then the patient file has the birth record includes the mother's street address "2878 Wescam Court"
    And the patient file has the birth record includes the mother's street address 2 "Apt 227"
    And the patient file has the birth record includes the mother's city "Saugus"
    And the patient file has the birth record includes the mother's county "Essex County"
    And the patient file has the birth record includes the mother's zip code "01906"
    And the patient file has the birth record includes the mother's state "Massachusetts"

  Scenario: I can retrieve Birth records with associated investigations for a patient
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And I can "view" any "investigation" for Geostigma in Midgar
    And the patient has a birth record
    And the birth record is associated with the investigation
    When I view the birth records for the patient
    Then the patient file has the birth record associated with the investigation

  Scenario: I cannot view associated investigations when retrieving Birth records for a patient when I do not have permission
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Neo-Tokyo"
    And the patient is a subject of an investigation for Geostigma within Neo-Tokyo
    And the patient has a birth record
    And the birth record is associated with the investigation
    When I view the birth records for the patient
    Then the patient file has the birth record not associated with any investigations
