@create_original_electronic_doc_list
Feature: Create Original Electronic Document List

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can create a original electronic document list static element
        Given I am logged in And I can "LDFAdministration" any "System"
        When I send a original electronic document list request
        Then a original electronic document list element is created

    Scenario: I cannot create a original electronic document list static element without logging in
        Given I am not logged in
        When I send a original electronic document list request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a original electronic document list static element without permissions
        Given I am logged in
        When I send a original electronic document list request
        Then an accessdenied exception is thrown