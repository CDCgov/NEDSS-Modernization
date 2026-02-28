
Feature: Patient Search by contact

  Background:
    Given I am logged in as secure user

  Scenario: Contact - Search by Phone Number
    When I search by phone number as "232-322-2222"
    Then I should see Results with the phone number as "232-322-2222"

  Scenario: Contact - Search by Email
    When I search by email as "fdsfs@dsds.com"
    Then I should see Results with the email as "fdsfs@dsds.com"

  Scenario: Contact - search by both phone number and email
    When I search by email as "fdsfs@dsds.com"
    And I search by phone number as "232-322-2222"
    Then I should see "Singh, Surma"

  Scenario: Contact - Search by partial phone number
    When I search by phone number as "23232"
    Then I should see Results with the phone number as "232-32"
