@page-builder-options @page-name
Feature: Page Name Options

  Background:
    Given I am logged in

  Scenario: I can find Page name options
    Given I have a page named "Template Page"
    And the page is a Template
    And I have a page named "Legacy Page"
    And the page is Legacy
    And I have a page named "pluvial"
    And I have a page named "colubrine"
    And I have a page named "plagium"
    And I have a page named "misology"
    And I have a page named "eupraxia"
    When I retrieve the selectable page names
    Then the option named "pluvial" with value "pluvial" is included
    And the option named "colubrine" with value "colubrine" is included
    And the option named "plagium" with value "plagium" is included
    And the option named "misology" with value "misology" is included
    And the option named "eupraxia" with value "eupraxia" is included

  Scenario: I can find Page name options that start with
    Given I have a page named "Template Page"
    And the page is a Template
    And I have a page named "Legacy Page"
    And the page is Legacy
    And I have a page named "pluvial"
    And I have a page named "colubrine"
    And I have a page named "quackle"
    And I have a page named "quicksticks"
    And I have a page named "quinology"
    And I have a page named "quattrocento"
    When I am trying to find selectable page names that start with "qui"
    Then there are 2 options included

  Scenario: I can find a specific number of Page name options
    Given I have a page named "Template Page"
    And the page is a Template
    And I have a page named "Legacy Page"
    And the page is Legacy
    And I have a page named "pluvial"
    And I have a page named "colubrine"
    And I have a page named "quackle"
    And I have a page named "quicksticks"
    And I have a page named "quinology"
    And I have a page named "quattrocento"
    And I have a page named "quod"
    When I am trying to find at most 4 selectable page names that start with "qu"
    Then there are 4 options included

  Scenario: I cannot find Page names that do not exist
    When I am trying to find selectable page names that start with "nothing"
    Then there aren't any options
