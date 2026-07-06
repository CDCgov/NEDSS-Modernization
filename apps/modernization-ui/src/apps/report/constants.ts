import { ReportConfiguration } from 'generated';
import { permissions } from 'libs/permission';
import { EnumSelectable } from './utils.ts';

export const PERMISSION_GROUP_MAP: Record<ReportConfiguration.group, typeof permissions.reports.private> = {
    [ReportConfiguration.group.PRIVATE]: permissions.reports.private,
    [ReportConfiguration.group.PUBLIC]: permissions.reports.public,
    [ReportConfiguration.group.TEMPLATE]: permissions.reports.template,
    [ReportConfiguration.group.REPORTING_FACILITY]: permissions.reports.reportingFacility,
};

export const GROUP_OPTIONS: EnumSelectable<ReportConfiguration.group>[] = [
    { value: ReportConfiguration.group.PRIVATE, name: 'Private' },
    { value: ReportConfiguration.group.PUBLIC, name: 'Public' },
    { value: ReportConfiguration.group.TEMPLATE, name: 'Template' },
    { value: ReportConfiguration.group.REPORTING_FACILITY, name: 'Reporting Facility' },
];

export const SIZING = 'medium';
