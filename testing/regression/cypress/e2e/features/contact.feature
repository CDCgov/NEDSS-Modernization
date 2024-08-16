
Feature: Patient Search by contact

  Background:
    Given I am logged in as secure user

  Scenario: Contact - Search by Phone Number
    When I search by phone number as "202-555-5509"
    Then I should see Results with the phone number as "202-555-5509"

  Scenario: Contact - Search by Email
    When I search by email as "paris.penn@example.com"
    Then I should see Results with the email as "paris.penn@example.com"

  Scenario: Contact - search by both phone number and email
    When I search by email as "none2@none.com"
    And I search by phone number as "202-555-0004"
    Then I should see "Lemmy Priestley"

  Scenario: Contact - Search by partial phone number
    When I search by phone number as "202555"
    Then I should see Results with the phone number as "202-555"
