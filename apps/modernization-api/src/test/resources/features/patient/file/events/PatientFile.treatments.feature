@patient-file-treatment
Feature: Patient File Treatments

  Background:
    Given I am logged in
    And I can "view" any "Treatment"
    And I have a patient

  Scenario: I cannot retrieve treatments for a patient not the subject of any treatments
    When I view the treatments for the patient
    Then no values are returned

  Scenario: I can retrieve treatments for a patient
    Given the patient is a subject of a Laser Surgery treatment
    And the treatment was treated on 02/03/2015
    And the treatment was created on 04/25/2010 at 11:15:17
    When I view the treatments for the patient
    And the patient file has the treatment created on 04/25/2010 at 11:15:17
    And the patient file has the treatment as "Laser Surgery"
    And the patient file has the treatment treated on 02/03/2015
    And the patient file has the treatment is not associated with any investigations

  Scenario: I can retrieve custom treatments for a patient
    Given the patient is a subject of the custom "Chicken Noodle Soup" treatment
    When I view the treatments for the patient
    And the patient file has the treatment as "Chicken Noodle Soup"

  Scenario: I can retrieve Treatments for a patient with a provider
    Given there is an organization named "Childrens Hospital"
    And there is a provider named "Arthur" "Childrens"
    And the patient is a subject of a Treatment
    And the treatment was reported at Childrens Hospital
    And the treatment was provided by the provider
    When I view the treatments for the patient
    Then the patient file has the treatment reported at "Childrens Hospital"
    And the patient file has the treatment provided by "Arthur" "Childrens"

  Scenario: I can retrieve Treatments with associated investigations for a patient
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And I can "view" any "investigation" for Geostigma in Midgar
    And the patient is a subject of a Treatment
    And the treatment is associated with the investigation
    When I view the treatments for the patient
    Then the patient file has the treatment associated with the investigation

  Scenario: I cannot view associated investigations when retrieving Treatments for a patient when I do not have permission
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And the patient is a subject of a Treatment
    And the treatment is associated with the investigation
    When I view the treatments for the patient
    Then the patient file has the treatment is not associated with any investigations
