Feature: I can view an algorithm's summary

  Background: 
    Given I am a ELR Importer user
    And I create a new Case Report system
    And I create a new Laboratory Report system

  Scenario: I can create a Laboratory Report algorithm
    Given I am a ELR Importer user
    When I create a Laboratory Report algorithm
    Then I can view the Laboratory Report algorithm summary

  Scenario: I can create a Case Report algorithm
    Given I am a ELR Importer user
    When I create a Case Report algorithm
    Then I can view the Case Report algorithm summary

  Scenario: I can edit a Case Report algorithm
    Given I am a ELR Importer user
    And I create a Case Report algorithm
    When I edit the Case Report algorithm
    Then I can view the edited Case Report algorithm summary
