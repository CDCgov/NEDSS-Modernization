Feature: Generate ELR HL7

  Background:
    Given I am logged in as "superuser" and password "@test"

  Scenario: ID - Check Search after HL7 ELR is created
    When I Generate HL7 messages with "AIDS" to api and mark as review