@display_control_options
Feature: display control options

    Scenario: I can get list of question display control options
        Given I am an admin user
        When I get list of question display control options
        Then return list of question display control options


    Scenario: I cannot get list of question display control options without logging in
        Given I am not logged in
        When I get list of question display control options
        Then a no credentials found exception is thrown

    Scenario: I cannot get list of question display control options without permissions
        Given I am a user without permissions
        When I get list of question display control options
        Then an accessdenied exception is thrown





