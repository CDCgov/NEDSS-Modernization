Feature: Generate ELR HL7

  Background:
    Given I am logged in as secure user
    Given I login for HL7 API generate token

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "Hep B" messages to api
    When I Check the HL7 transport uid