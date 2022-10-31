Feature: Drug test search

  Scenario Outline: Lab drug searches return expected values
    Given I am a data entry user
    When I do a "<type>" search for "<drug>"
    Then I can see the result "<code>"

    Examples: 
      | drug           | type  | code                      |
      | AZLOCILLIN     | long  |                   18867-2 |
      | FLUOROCYTOSINE | long  |                   18855-7 |
      | AMANTADINE     | long  |                     38145 |
      | AMANTADINE     | short |                     37779 |
      | (2 DRUGS)      | short |                    088005 |
      | &              | short | Nothing found to display. |
      | &amp;          | short | Nothing found to display. |
      | &#39;          | short | Nothing found to display. |
      | backslash      | short | Nothing found to display. |
      | zzzzzz         | long  | Nothing found to display. |
