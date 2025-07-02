@client-permissions
Feature: Permissions are available on the client

  Background:
    Given I am logged into NBS
    And there is a program area named "Greyscale"
    And there is a program area named "Entitilitus"
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
      | "View"    | "Investigation" | Greyscale    | Coruscant    |
      | "View"    | "Patient"       | Entitilitus  | Pawnee       |

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
      | "View"    | "Investigation" | Entitilitus  | Coruscant    |
      | "View"    | "Patient"       | Greyscale    | Pawnee       |
  
  Scenario Outline: A user is a master system administrator
    And I am a master system administrator
    When I access NBS from the client
    Then I am able to "ADMINISTRATOR" "SYSTEM" from the client
  
  Scenario Outline: A user is a security administrator
    And I am a security administrator
    When I access NBS from the client
    Then I am able to "ADMINISTRATOR" "SECURITY" from the client

