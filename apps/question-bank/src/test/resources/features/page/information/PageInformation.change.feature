@page-information-change
Feature: Changing Page Information

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can change the Message Mapping Guide of a Page
    Given I have an Investigation page named "Testing Information" mapped by Generic Case Notification Message Mapping Guide v.2
    And I want to change the page Message Mapping Guide to No Reporting Mechanism (Not NND Reportable)
    When I change the page information
    Then I retrieve the information of a page
    And the page information should have a "Message Mapping Guide" equal to "No Reporting Mechanism (Not NND Reportable)"

  Scenario: I can change the name of a Page
    Given I have an Investigation page named "Testing Information"
    And I want to change the page "name" to "Changed Name"
    When I change the page information
    Then I retrieve the information of a page
    And the page information should have a "name" equal to "Changed Name"

  Scenario: I cannot change the name of a Page to a name used by another Page
    Given I have an Investigation page named "Testing Information"
    And I have another page
    And I want to change the page "name" to "Testing Information"
    When I change the page information
    Then the page information cannot be changed because "Another Page is named Testing Information"

  Scenario: I can change the datamart of a Page
    Given I have an Investigation page named "Testing Information"
    And I want to change the page "datamart" to "Changed Datamart"
    When I change the page information
    Then I retrieve the information of a page
    And the page information should have a "datamart" equal to "Changed Datamart"

  Scenario: I cannot change the datamart of a Page to a datamart used by another Page
    Given I have an Investigation page named "Testing Information"
    And the page has a "datamart" of "Existing Datamart"
    And I have another page
    And I want to change the page "datamart" to "Existing Datamart"
    When I change the page information
    Then the page information cannot be changed because "Another Page is using the datamart named Existing Datamart"


  Scenario: I cannot change the datamart of page that has been published
    Given I have an Investigation page named "Testing Information"
    And the page is Published
    And I want to change the page "datamart" to "Changed Datamart"
    When I change the page information
    Then the page information cannot be changed because "Changes can only be made to a Draft page"

  Scenario: I cannot change the datamart of page that has ever been published
    Given I have an Investigation page named "Testing Information"
    And the page is Published with Draft
    And I want to change the page "datamart" to "Changed Datamart"
    When I change the page information
    Then the page information cannot be changed because "The datamart cannot be changed if the Page had ever been Published"

  Scenario: I can change the description of a Page
    Given I have an Investigation page named "Testing Information"
    And I want to change the page "description" to "Changed Description"
    When I change the page information
    Then I retrieve the information of a page
    And the page information should have a "description" equal to "Changed Description"

  Scenario: I can change the Conditions associated with a Page
    Given I have an Investigation page named "Testing Information"
    And I want to change the page associations to include Plague
    When I change the page information
    Then I retrieve the information of a page
    And the page information should have a "condition" equal to "Plague"

  Scenario: I cannot associate Conditions with a Page that has ever been Published
    Given I have an Investigation page named "Testing Information"
    And the page is Published
    And I want to change the page associations to include Plague
    When I change the page information
    Then the page information cannot be changed because "Changes can only be made to a Draft page"

  Scenario: I can change the Conditions associated with a Page
    Given I have an Investigation page named "Testing Information"
    And the page is associated with the Plague condition
    When I change the page information
    Then I retrieve the information of a page
    And the page information should not have a "condition" equal to "Plague"

  Scenario: I can change the Conditions associated with a Page
    Given I have an Investigation page named "Testing Information"
    And the page is associated with the Plague condition
    And the page is Published
    When I change the page information
    Then the page information cannot be changed because "Changes can only be made to a Draft page"

  Scenario: I cannot disassociate Conditions with a Page that has ever been Published
    Given I have an Investigation page named "Testing Information"
    And the page has a "datamart" of "datamart"
    And the page is associated with the Plague condition
    And the page is Published with Draft
    And I want to change the page "datamart" to "datamart"
    When I change the page information
    Then the page information cannot be changed because "The related conditions cannot be changed if the Page had ever been Published"
