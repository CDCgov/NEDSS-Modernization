@patient_extended_create
Feature: Creation of Patients with extended data permission

  Background:
    Given I am logged into NBS

  Scenario: I can not create a patient without Add patient permissions
    When I create a patient with extended data
    Then I am not allowed due to insufficient permissions
