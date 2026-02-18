package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;

public record DocumentsRequiringReviewCriteria(
    long patient,
    PermissionScope documentScope,
    PermissionScope labReportScope,
    PermissionScope morbidityReportScope,
    PermissionScope treatmentScope,
    PermissionScope investigationScope) {}
