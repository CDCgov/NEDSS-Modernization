package gov.cdc.nbs.patient.documentsrequiringreview;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;

record DocumentsRequiringReviewCriteria(
    long patient,
    PermissionScope documentScope,
    PermissionScope labReportScope,
    PermissionScope morbidityReportScope
) {
}
