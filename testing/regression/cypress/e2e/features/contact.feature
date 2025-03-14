
Feature: Patient Search by contact

  Background:
    Given I am logged in as secure user

  Scenario: Contact - Search by Phone Number
    When I search by phone number as "732-721-2970"
    Then I should see Results with the phone number as "732-721-2970"

  Scenario: Contact - Search by Email
    When I search by email as "CadenRatkeyklkb79@hotmail.com"
    Then I should see Results with the email as "CadenRatkeyklkb79@hotmail.com"

  Scenario: Contact - search by both phone number and email
    When I search by email as "CadenRatkeyklkb79@hotmail.com"
    And I search by phone number as "732-721-2970"
    Then I should see "Ratkeyklkb, Caden Benjamin"

  Scenario: Contact - Search by partial phone number
    When I search by phone number as "732721"
    Then I should see Results with the phone number as "732-721"
