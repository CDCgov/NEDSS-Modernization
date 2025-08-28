@patient-edit
Feature: Editing of Patient demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I have a patient

    Scenario: I can not edit a patient that does not exist
      When I edit an unknown patient with entered demographics
      Then I am unable to edit the patient because it was not found
