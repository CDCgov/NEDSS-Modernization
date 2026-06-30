import { ReportConfiguration } from 'generated';
import { permissions } from 'libs/permission';

export const PERMISSION_GROUP_MAP = {
    [ReportConfiguration.group.PRIVATE]: permissions.reports.private,
    [ReportConfiguration.group.PUBLIC]: permissions.reports.public,
    [ReportConfiguration.group.TEMPLATE]: permissions.reports.template,
    [ReportConfiguration.group.REPORTING_FACILITY]: permissions.reports.reportingFacility,
};
