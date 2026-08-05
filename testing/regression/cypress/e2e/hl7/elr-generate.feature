@skip-if-no-di-api
Feature: Generate ELR HL7

  Background:
    Given I am logged in as secure user
    And I login for HL7 API generate token

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "hepb" messages to api
    And I Check the HL7 transport uid

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "lyme" messages to api
    And I Check the HL7 transport uid

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "syphilis" messages to api
    And I Check the HL7 transport uid

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "gonorrhea" messages to api
    And I Check the HL7 transport uid

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "chlamydia" messages to api
    And I Check the HL7 transport uid

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "hiv" messages to api
    And I Check the HL7 transport uid
