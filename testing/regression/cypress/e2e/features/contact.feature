
Feature: Patient Search by contact

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: Contact - Search by Phone Number
    Then I navigate to contact section
    When I search by phone number as "202-555-5509"
    Then I should see Results with the phone number as "202-555-5509"

  Scenario: Contact - Search by Email
    Then I navigate to contact section
    When I search by email as "paris.penn@example.com"
    Then I should see Results with the email as "paris.penn@example.com"

Scenario: Contact - search by both phone number and email
  Then I navigate to contact section
  When I search by email as "none2@none.com"
  And I search by phone number as "202-555-0004"
  Then I should see "Lemmy Hansel Priestley, Junior"
