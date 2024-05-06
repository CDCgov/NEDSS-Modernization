
Feature: Patient Search by Condition

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: Condition - Search by Condition
    When I navigate the event investigation
    When I select a condition for event investigation
    Then I should see Results with the "Condition" as "Acanthamoeba Disease (Excluding Keratitis)"