Feature: Manage Receiving Systems

  Scenario: I can create a new case report system
    Given I am a ELR Importer user
    When I create a new Case Report system
    Then I can view the Case Report system details

  Scenario: I can create a new laboratory report system
    Given I am a ELR Importer user
    When I create a new Laboratory Report system
    Then I can view the Laboratory Report system details

  Scenario: I can edit a case report system
    Given I am a ELR Importer user
    And I create a new Case Report system
    When I make changes to the Case Report system
    Then I can view the edited Case Report system details

  Scenario: I can edit a laboratory report system
    Given I am a ELR Importer user
    And I create a new Laboratory Report system
    When I make changes to the Laboratory Report system
    Then I can view the edited Laboratory Report system details
