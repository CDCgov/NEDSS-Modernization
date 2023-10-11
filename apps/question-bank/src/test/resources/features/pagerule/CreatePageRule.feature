@create_pagerule
Feature: Create page rule

    Scenario: I can create a page rule
        Given I make a request to add page rule request
        When I make a request to page rule add
        Then A page rule is created

    Scenario: I cannot create a page rule
        Given I make a request to create page rule without rule request
        When I make a request to page rule add
        Then A page rule is not created