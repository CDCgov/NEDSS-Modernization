Feature: I can find a questionnaire

  Scenario: I can find a questionnaire by specifying the condition Id and the questionnaire type
    Given a questionnaire exists
    When i search for a questionnaire
    Then the questionnaire is returned
