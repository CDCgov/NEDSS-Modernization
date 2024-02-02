Feature: Frontend Properties from NBS Configuration

  Scenario: NBS Configuration values are available through the API
    Given NBS is configured with a "CODE_BASE" of "Testing - 157"
    And NBS is configured with an "STD_PROGRAM_AREAS" of "1063,839"
    And NBS is configured with an "HIV_PROGRAM_AREAS" of "971"
    When I request the frontend configuration
    Then the properties include a code base of "Testing - 157"
    And the properties include an STD Program Areas with "1063"
    And the properties include an STD Program Areas with "839"
    And the properties include an HIV Program Areas with "971"
