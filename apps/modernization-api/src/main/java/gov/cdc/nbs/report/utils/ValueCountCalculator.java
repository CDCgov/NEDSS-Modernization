package gov.cdc.nbs.report.utils;

import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.UpsertFilterRequest;

public class ValueCountCalculator {
  private ValueCountCalculator() {}

  public record ReportValueCounts(Integer minValueCount, Integer maxValueCount) {}

  // Suppressing java:S131 ("switch" statements should have "default" clauses) because
  // there isn't anything to actually do in the default case when switching against
  // the filter request's filterCodeUid.
  @SuppressWarnings("java:S131")
  public static ReportValueCounts fromFilterRequest(UpsertFilterRequest filterRequest) {
    Integer minValueCount = null;
    Integer maxValueCount = null;

    // Note that this logic mapping filterCodeUids to corresponding min value counts and
    // max value counts was pulled **verbatim** from NBS 6, to maintain parity with 6 as
    // closely as possible.
    switch (filterRequest.filterCodeUid().intValue()) {
      case 7 // Where Clause Builder
          -> {
        minValueCount = 0;
        maxValueCount = -1;
      }
      case 5, // Time Range
          6, // Time Period
          12, // Time Range (Including NULLS)
          13, // Time Period (Including NULLS)
          14, // Month Year Range
          15, // Month Year Range (Including NULLS)
          17, // Time Range Custom
          18 // Basic Text Filter
          -> {
        minValueCount = 1;
        maxValueCount = 2;
      }
      case 1, // Diseases
          2, // States
          3, // Counties
          8, // Diseases (Including NULLS)
          9, // States (Including NULLS)
          10, // Counties (Including NULLS)
          16, // Case Diagnosis
          19, // STD Case Diagnosis
          20, // HIV Case Diagnosis
          21 // STD HIV Workers
          -> {
        switch (filterRequest.selectType()) {
          case ReportConstants.SelectType.MULTI -> {
            minValueCount = 1;
            maxValueCount = -1;
          }
          case ReportConstants.SelectType.SINGLE -> {
            minValueCount = 1;
            maxValueCount = 1;
          }
        }
      }
    }

    return new ReportValueCounts(minValueCount, maxValueCount);
  }

  public static ReportConstants.SelectType toSelectType(ReportValueCounts counts) {
    if ((Integer.valueOf(1)).equals(counts.minValueCount)) {
      if (Integer.valueOf(-1).equals(counts.maxValueCount)) return ReportConstants.SelectType.MULTI;
      if (Integer.valueOf(1).equals(counts.maxValueCount)) return ReportConstants.SelectType.SINGLE;
    }

    return null;
  }
}
