Feature: User can add a patient profile for extended form

    Background:
        Given I am logged in as secure user

    Scenario: User add a patient and enters a comment
        Then the user has searched for a patient by "Person number" as "63506872864" 
        When nagivate to add new patient page
        Then nagivate to extended form
        Then the user adds invalid long comment