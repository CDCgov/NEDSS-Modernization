Feature: Generate ELR HL7

  Background:
    Given I am logged in as secure user
    Given I login for HL7 API generate token

  Scenario: ID - Create ELR auto Notification
    When I Generate HL7 "Hep B" messages to api

  Scenario: ID - Create ELR manually create Investigation and Notification
    When I Generate HL7 message, create investigation "AIDS", and submit a notification