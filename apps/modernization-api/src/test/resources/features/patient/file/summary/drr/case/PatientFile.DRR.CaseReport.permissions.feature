@documents-requiring-review @case-report
Feature: Patient File Documents Requiring Review: Case Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And there is a program area named "Greyscale"
    And there is a jurisdiction named "Old Valria"
    And there is a program area named "Krippin"
    And there is a jurisdiction named "Greenwich Village"

    And I have a patient
    And the patient has a Case Report


  Scenario: I can only view Cases Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "document" for Krippin in Greenwich Village
    And the case report has not been processed
    And  the case report is for Krippin within Greenwich Village
    When I view the documents requiring review for the patient
    Then the patient file has the Case Report requiring review

  Scenario: I can not view Cases Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "document" for Krippin in Greenwich Village
    And the case report has not been processed
    And  the case report is for Greyscale within Greenwich Village
    When I view the documents requiring review for the patient
    Then the patient file has no Case Reports requiring review

  Scenario: I can only view Cases Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "document" for Greyscale in Old Valria
    And the case report has not been processed
    And  the case report is for Greyscale within Old Valria
    When I view the documents requiring review for the patient
    Then the patient file has the Case Report requiring review

  Scenario: I can not view Cases Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "document" for Greyscale in Old Valria
    And the case report has not been processed
    And  the case report is for Krippin within Old Valria
    When I view the documents requiring review for the patient
    Then the patient file has no Case Reports requiring review

