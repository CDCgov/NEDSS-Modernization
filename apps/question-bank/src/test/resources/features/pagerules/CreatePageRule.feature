@create_business_rule
Feature: Create Business Rule

    Background:
        Given I am logged in 
        And I can "LDFAdministration" any "System"
    
    Scenario: I can create a require if business rule with multiple source values
        Given I have a page
        And the page is a Draft
        And the business rule has "source text" of "Current Sex"
        And the business rule has "source identifier" of "DEM113"
        And the business rule has "rule description" of "require if"
        And the business rule has "function" of "require if"
        And the business rule has "target type" of "Question"
        And the business rule has "any source value" of "false"
        And the business rule has "comparator" of "="
        And the business rule has "source value ids" of: 
            |M|
            |F|
        And the business rule has "target values list" of: 
            |Age at Onset|
        And the business rule has "target identifiers list" of: 
            |INV143|
        And the business rule has "source value texts" of: 
            |Male|
            |Female|
        When I send the page rule create request
        Then I retrieve the information of the page rule
        And the business rule should have "function" of "require if"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "require if"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have "target identifiers list" of: 
            | INV143 |
        And the business rule should have "source values" of: 
            | Male |
            | Female |