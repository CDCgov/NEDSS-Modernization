@delete_pagerule
Feature: Delete page rule

    Scenario: I can clear a page rule
        Given I make a request to delete page rule request
        When I make a request to page rule delete
        Then A page rule is deleted

    Scenario: I cannot delete a page rule
        Given I make a request to delete page rule without rule request
        When I make a request to page rule delete
        Then A page rule is not deleted
