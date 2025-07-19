@patient-edit
Feature: Editing of Patient demographics permission

  Background:
    Given I am logged into NBS
    And I have a patient

  Scenario: I can not edit a patient without edit patient permissions
    When I edit a patient with entered demographics
    Then I am not allowed due to insufficient permissions
