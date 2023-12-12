@page_update
Feature: Update Page

    Background:
        Given I am logged in
        And I can "LDFAdministration" any "System"

    Scenario: I can publish draft page 
        Given I have a page
        And the page is a Draft
        And the publish page request has version notes of "testing publishing"
        When I send the publish page request
        Then the response of request is success
        