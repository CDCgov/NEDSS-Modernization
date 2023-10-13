@page_reorder
Feature: Reorder page content

    Background:
        Given pages exist

    @tab_reorder
    Scenario: I can reorder a tab
        Given I am an admin user
        When I send a reorder request for a tab
        Then the tab is reordered

    @reorder_permission
    Scenario: I cannot reorder without being logged in
        Given I am not logged in
        When I send a reorder request for a tab
        Then a no credentials found exception is thrown

    @reorder_permission
    Scenario: I cannot reorder without the proper permissions
        Given I am a user without permissions
        When I send a reorder request for a tab
        Then an accessdenied exception is thrown

    @section_reorder
    Scenario: I can reorder a section
        Given I am an admin user
        When I send a reorder request for a section
        Then the section is reordered

    @subsection_reorder
    Scenario: I can reorder a subsection
        Given I am an admin user
        When I send a reorder request for a subsection
        Then the subsection is reordered

    @question_reorder
    Scenario: I can reorder a question
        Given I am an admin user
        When I send a reorder request for a question
        Then the question is reordered