@feature_flag
Feature: Feature Flagging

    Scenario: I can retrieve the current feature flag configuration
        Given I am logged into NBS
        When I request the current configuration
        Then the current configuration is returned