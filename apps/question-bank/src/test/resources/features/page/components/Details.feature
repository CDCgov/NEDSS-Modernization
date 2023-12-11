@page @page-component
Feature: Page Components

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page

  Scenario: I can retrieve a draft Page
    Given I have a page named "Front Page"
    And the page has a "description" of "description value"
    When I view the components of a page
    Then the page should have a "name" equal to "Front Page"
    And the page should have a "status" equal to "Draft"
    And the page should have a "description" equal to "description value"
    And the page should have a component root

  Scenario: I can retrieve a published Page
    Given I have a page named "Front Page"
    And the page is Published
    When I view the components of a page
    Then the page should have a "status" equal to "Published"

  Scenario: I can retrieve a draft of a published Page
    Given I have a page named "Front Page"
    And the page is Published with draft
    When I view the components of a page
    Then the page should have a "status" equal to "Draft"

  Scenario: I can retrieve the tabs of a Page
    Given the page has a tab
    And the page has a tab named "Next Tab"
    And the page has another tab
    When I view the components of a page
    Then the page should have a "tab" named "Next Tab" with an "order" of 3
    And the page should have a "tab" named "Next Tab" that is "visible"

  Scenario: I can retrieve the sections of a Page
    Given the page has a tab named "Tab one"
    And the page has a tab named "Tab two"
    And the page has another tab
    And the page has a section named "Section One A" in the "Tab one" tab
    And the page has a section named "Section Other" in the 3rd tab
    And the page has a section named "Section Two A" in the "Tab two" tab
    When I view the components of a page
    Then the page should have a "tab" named "Tab one" with an "order" of 2
    And the page should have a "section" named "Section One A" with an "order" of 3
    And the page should have a "section" named "Section One A" that is "visible"
    And the page should have a "tab" named "Tab two" with an "order" of 4
    And the page should have a "section" named "Section Two A" with an "order" of 5
    And the page should have a "section" named "Section Two A" that is "visible"
    And the page should have a "section" named "Section Other" with an "order" of 7
    And the page should have a "section" named "Section Other" that is "visible"

  Scenario: I can retrieve the sub-sections of a Page
    Given the page has a tab
    And the page has another tab
    And the page has a section named "One" in the 1st tab
    And the page has a section named "Two" in the 2nd tab
    And the page has a sub-section named "Sub-Section B" in the 2nd section
    And the page has a sub-section named "Sub-Section A" in the "One" section
    When I view the components of a page
    Then the page should have a "sub-section" named "Sub-Section A" with an "order" of 4
    And the page should have a "sub-section" named "Sub-Section A" that is "visible"
    And the page should have a "sub-section" named "Sub-Section B" with an "order" of 7
    And the page should have a "sub-section" named "Sub-Section B" that is "visible"

  Scenario: I can retrieve the questions of a Page
    Given the page has a tab
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And the page has another sub-section in the 1st section
    And the page has a question named "Question Two" in the 2nd sub-section
    And the page has a question named "Question One" in the 1st sub-section
    When I view the components of a page
    Then the page should have a "question" named "Question One" with an "order" of 5
    And the page should have a "question" named "Question One" that is "enabled"
