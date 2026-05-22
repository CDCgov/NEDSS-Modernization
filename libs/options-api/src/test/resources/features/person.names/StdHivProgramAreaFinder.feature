@request
Feature: STD HIV Program Area Finder Utility

  Background:
    Given the HIV program areas are:
      | HIV |
      | AIDS |
      | HIV-AIDS |
    Given the STD program areas are:
      | STD  |
      | SYPHILIS |
      | GONORRHEA |

  Scenario: I can fetch all the STD and HIV program areas
    When I get the STD HIV program area values
    Then the result should contain the following values:
      | HIV |
      | AIDS |
      | HIV-AIDS |
      | STD  |
      | SYPHILIS |
      | GONORRHEA |
