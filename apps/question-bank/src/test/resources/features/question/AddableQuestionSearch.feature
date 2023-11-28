@addable_question_search
Feature: Addable Question Search

  Background:
    Given pages exist
    And A text question exists
    And A date question exists
    And I am logged in
    And I can "LDFADMINISTRATION" any "SYSTEM"

  Scenario: I can search for questions that can be added to a page
    When I search for addable questions by "<query>" and sorted by "<sortField>" <direction>
    Then the "<expected>" addable questions are returned
    Examples:
      | query                | sortField  | direction  | expected                |
      | Question Unique Name | type       | ascending  | TEST9900001,TEST9900002 |
      | Question Unique Name | type       | descending | TEST9900002,TEST9900001 |
      | Question Unique Name | uniqueId   | ascending  | TEST9900001,TEST9900002 |
      | Question Unique Name | uniqueId   | descending | TEST9900002,TEST9900001 |
      | Question Unique Name | uniqueName | ascending  | TEST9900002,TEST9900001 |
      | Question Unique Name | uniqueName | descending | TEST9900001,TEST9900002 |
      | Question Unique Name | subgroup   | ascending  | TEST9900001,TEST9900002 |
      | Question Unique Name | subgroup   | descending | TEST9900002,TEST9900001 |

  Scenario: I can search for questions to add to a page on multiple fields
    When I search for addable questions by "<query>"
    Then a question with a "<field>" matching "<query>" is returned
    Examples:
      | query                      | field        |
      | PHIN                       | type         |
      | LOCAL                      | type         |
      | Date Question Unique Name  | uniqueName   |
      | TEST9900001                | uniqueId     |
      | Administrative Information | subgroupName |