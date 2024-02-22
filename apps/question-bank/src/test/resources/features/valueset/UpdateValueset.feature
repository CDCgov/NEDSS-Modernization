@valueset_update
Feature: I can update a Value set

  Background:
    Given I am logged in
    And I can "LDFADMINISTRATION" any "SYSTEM"
    And I send a request to create a valueset with type "LOCAL", code "test_update_value_set", name "updateVSName", and description "original description"

  Scenario: Update value set
    Given I have a request to update a value set with name "new_Vs_Name" and description "updated description"
    When I send a request to update the value set "test_update_value_set"
    Then the value set is updated
