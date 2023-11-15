@page @create-template
Feature: Create a template from a Page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a template from a page
    Given I have an Investigation page named "Testing Template Creation"
    When I want to create a template named "Feature Testing Template"
    And I want to create a template described as "Created for feature testing of Template Creation"
    And I create a template from the page
    Then the page template is created using NBS Classic

  Scenario: I cannot create a template from a page when the template name already exists
    Given I have an Investigation page named "Existing Template"
    And the page is a Template
    And I have an Investigation page named "Testing Template Creation"
    When I want to create a template named "Existing Template"
    And I want to create a template described as "Created for feature testing of Template Creation"
    And I create a template from the page
    Then the template cannot be changed because "Another Template is named Existing Template"

  Scenario: I cannot create a template from a page without a name
    Given I have an Investigation page named "Testing Template Creation"
    When I want to create a template described as "Created for feature testing of Template Creation"
    And I create a template from the page
    Then the template cannot be changed because "A Template name is required"

  Scenario: I cannot create a template from a page when the name is blank
    Given I have an Investigation page named "Testing Template Creation"
    When I want to create a template named ""
    And I want to create a template described as "Created for feature testing of Template Creation"
    And I create a template from the page
    Then the template cannot be changed because "A Template name is required"

  Scenario: I cannot create a template from a page without a description
    Given I have an Investigation page named "Testing Template Creation"
    When I want to create a template named "Feature Testing Template"
    And I create a template from the page
    Then the template cannot be changed because "A Template description is required"

  Scenario: I cannot create a template from a page when the description is blank
    Given I have an Investigation page named "Testing Template Creation"
    When I want to create a template named "Feature Testing Template"
    And I want to create a template described as ""
    And I create a template from the page
    Then the template cannot be changed because "A Template description is required"
