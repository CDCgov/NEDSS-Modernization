@un_group_subsection
Feature: ungroup Subsection

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "whatever" in the 1st section

    Scenario: I can ungroup a subsection
        Given I am an admin user
        When I send a ungroup subsection request
        Then the subsection is ungrouped

    Scenario: I cannot ungroup a subsection without logging in
        Given I am not logged in
        When I send a ungroup subsection request
        Then a no credentials found exception is thrown

    Scenario: I cannot ungroup a section without permissions
        Given I am a user without permissions
        When I send a ungroup subsection request
        Then an accessdenied exception is thrown
