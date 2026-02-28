@skip-if-no-di-api
Feature: End to end testing of ELR processing

  Background:
    Given I am authenticated with the DI API
    Given I am logged in as secure user


  Scenario: A Hepatitis B notification is created automatically when ingesting an ELR
    Given I have a HL7 message containing a "Hepatitis B" test
    When I submit the HL7 message
    Then the HL7 message is processed by the data ingestion service
    And an Investigation is created for the HL7 message
    And the Investigation has a notification with a status of "UNPROCESSED"
    And the Notification is copied onto the on-prem database with a status of "queued"
