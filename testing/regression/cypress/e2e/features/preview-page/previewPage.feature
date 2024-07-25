Feature: Page Builder - User can view Preview Page here.

  Background:
    Given I am logged in as "superuser" and password "@test"

  Scenario Outline: Verify the data elements in the preview page header buttons linked with functionalities
    When User navigates to Preview Page and views the Preview Page
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is draft
    When User navigates to Preview Page and page status is Initial Draft
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is published
    When User navigates to Preview Page and page status is Published
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Preview          | Icon   |
      | Print            | Icon   |
      | Create new draft | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is published with draft
    When User navigates to Preview Page and page status is Published with Draft
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

