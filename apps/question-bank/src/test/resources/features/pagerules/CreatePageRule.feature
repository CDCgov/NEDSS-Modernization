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
        And the business rule has "rule description" of "Require If"
        And the business rule has "function" of "Require If"
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
        And the business rule should have "function" of "Require If"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "Require If"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have "target identifiers list" of:
            | INV143 |
        And the business rule should have "source values" of:
            | Male |
            | Female |


    Scenario: I can create a disable business rule with multiple source values
        Given I have a page
        And the page is a Draft
        And the business rule has "source text" of "Current Sex"
        And the business rule has "source identifier" of "DEM113"
        And the business rule has "rule description" of "Disable"
        And the business rule has "function" of "Disable"
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
        And the business rule should have "function" of "Disable"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "Disable"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have target questions list of:
         | label          | id           |
         | Age at Onset   | INV143       |

        And the business rule should have "source values" of:
            | Male |
            | Female |

    Scenario: I can create a unhide business rule with multiple source values
        Given I have a page
        And the page is a Draft
        And the business rule has "source text" of "Current Sex"
        And the business rule has "source identifier" of "DEM113"
        And the business rule has "rule description" of "Unhide"
        And the business rule has "function" of "Unhide"
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
        And the business rule should have "function" of "Unhide"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "Unhide"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have target questions list of:
            | label          | id           |
            | Age at Onset   | INV143       |
        And the business rule should have "source values" of:
            | Male |
            | Female |

    Scenario: I can create a hide business rule with multiple source values
        Given I have a page
        And the page is a Draft
        And the business rule has "source text" of "Current Sex"
        And the business rule has "source identifier" of "DEM113"
        And the business rule has "rule description" of "Hide"
        And the business rule has "function" of "Hide"
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
        And the business rule should have "function" of "Hide"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "Hide"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have target questions list of:
            | label          | id           |
            | Age at Onset   | INV143       |
        And the business rule should have "source values" of:
            | Male |
            | Female |

    Scenario: I can create a Enable business rule with multiple source values
        Given I have a page
        And the page is a Draft
        And the business rule has "source text" of "Current Sex"
        And the business rule has "source identifier" of "DEM113"
        And the business rule has "rule description" of "Enable"
        And the business rule has "function" of "Enable"
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
        And the business rule should have "function" of "Enable"
        And the business rule should have "source identifier" of "DEM113"
        And the business rule should have "rule description" of "Enable"
        And the business rule should have "comparator" of "="
        And the business rule should have "target type" of "Question"
        And the business rule should have target questions list of:
            | label          | id           |
            | Age at Onset   | INV143       |
        And the business rule should have "source values" of:
            | Male |
            | Female |
