package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;

public record AdminEditReportRequest(
    Long libraryId,
    String reportTitle,
    String sectionCode,
    String description,
    Long ownerId, //  TODO: Should an admin be able to edit ownerId?
    ReportConstants.ReportGroup group,
    CreateReportRequest.ReportExecutionConfiguration executionRequest) {}
