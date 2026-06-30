import { ReportConfiguration } from 'generated';
import { permissions } from 'libs/permission';

export const PERMISSION_GROUP_MAP = {
    [ReportConfiguration.group.PRIVATE]: permissions.reports.private,
    [ReportConfiguration.group.PUBLIC]: permissions.reports.public,
    [ReportConfiguration.group.TEMPLATE]: permissions.reports.template,
    [ReportConfiguration.group.REPORTING_FACILITY]: permissions.reports.reportingFacility,
};

export const BASIC_SECTIONS = [
    {
        title: 'Time',
        id: 'basic-time',
        filterTypes: ['BAS_TIM_RANGE', 'BAS_TIM_RANGE_LIST', 'BAS_MM_YYYY_RANGE', 'BAS_TIM_RANGE_CUSTOM', 'BAS_DAYS'],
    },
    {
        title: 'Condition',
        id: 'basic-condition',
        filterTypes: ['BAS_CON_LIST', 'BAS_CVG_LIST'],
    },
    {
        title: 'Geographic area',
        id: 'basic-geography',
        filterTypes: ['BAS_JUR_LIST'],
    },
    {
        title: 'Other filters',
        id: 'basic-other',
        filterTypes: ['BAS_TXT', 'BAS_STD_HIV_WRKR'],
    },
];
