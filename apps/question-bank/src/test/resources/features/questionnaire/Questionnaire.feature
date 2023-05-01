Feature: I can find a questionnaire

  Scenario: I can find a questionnaire by specifying the condition Id and the questionnaire type
    Given a "syphilis" questionnaire exists with type "initial interview"
    And a "Brucellosis" questionnaire exists with type "field follow-up"
    When I search for a questionnaire
    Then the questionnaire is returned
