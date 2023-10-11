@update_pagerule
Feature: Update page rule

    Scenario: I can update a page rule
        Given I make a request to update page rule request
        When I make a request to page rule update
        Then A page rule is updated

    Scenario: I cannot update a page rule
        Given I make a request to update page rule without rule request
        When I make a request to page rule update
        Then A page rule is not updated
