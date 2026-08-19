package gov.cdc.nbs.report.models;

import java.time.LocalDateTime;

/** Model returned to the user/UI when they execute a report */
public record ReportExecutionResult(
    LibraryExecutionResult result, String query, LocalDateTime timestamp) {}
