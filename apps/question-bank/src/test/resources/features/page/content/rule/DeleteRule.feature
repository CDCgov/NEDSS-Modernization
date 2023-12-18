@delete_rule
Feature: Delete rule

    Background:
     Given I am logged in
        And I can "LDFAdministration" any "System"

    Scenario: I can delete a rule
        Given I have a page
        And the page has a rule
        When I send a delete rule request
        Then the rule is deleted

