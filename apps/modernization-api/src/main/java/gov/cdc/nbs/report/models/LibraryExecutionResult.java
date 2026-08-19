package gov.cdc.nbs.report.models;

import java.io.InputStream;

/** Model returned from the report-execution service when a library is invoked */
public record LibraryExecutionResult(
    InputStream content, String contextHeader, String description) {}
