@client-permissions
Feature: Permissions are available on the client

  Background:
    Given I am logged into NBS
    And there is a jurisdiction named "Coruscant"
    And there is a jurisdiction named "Pawnee"

  Scenario Outline: A user is permitted to <operation> any <object>
    And I can <operation> any <object>
    When I access NBS from the client
    Then I am able to <operation> <object> from the client

    Examples:
      | operation | object          |
      | "View"    | "Investigation" |
      | "View"    | "Patient"       |

  Scenario Outline: A user is permitted to <operation> any <object> for <program-area> within <jurisdiction>
    And I can <operation> any <object> for <program-area> in <jurisdiction>
    When I access NBS from the client
    Then I am able to <operation> <object> from the client

    Examples:
      | operation | object          | program-area | jurisdiction |
      | "View"    | "Investigation" | STD          | Coruscant    |
      | "View"    | "Patient"       | ARBO         | Pawnee       |

  Scenario Outline: A user is permitted to <operation> shared <object>s
    And I can <operation> a shared <object>
    When I access NBS from the client
    Then I am able to <operation> <object> from the client

    Examples:
      | operation | object          |
      | "View"    | "Investigation" |
      | "View"    | "Patient"       |

  Scenario Outline: A user is permitted to <operation> shared <object> for <program-area> within <jurisdiction>
    And I can <operation> a shared <object> for <program-area> in <jurisdiction>
    When I access NBS from the client
    Then I am able to <operation> <object> from the client

    Examples:
      | operation | object          | program-area | jurisdiction |
      | "View"    | "Investigation" | STD          | Coruscant    |
      | "View"    | "Patient"       | ARBO         | Pawnee       |
