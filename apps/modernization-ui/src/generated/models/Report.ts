/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataSource } from './DataSource';
import type { EffectiveTime } from './EffectiveTime';
import type { ReportFilter } from './ReportFilter';
import type { ReportId } from './ReportId';
import type { ReportLibrary } from './ReportLibrary';
import type { Status } from './Status';
export type Report = {
    id?: ReportId;
    dataSource?: DataSource;
    reportLibrary?: ReportLibrary;
    reportFilters?: Array<ReportFilter>;
    descTxt?: string;
    effectiveTime?: EffectiveTime;
    filterMode?: string;
    isModifiableIndicator?: string;
    location?: string;
    ownerUid?: number;
    orgAccessPermission?: string;
    progAreaAccessPermission?: string;
    reportTitle?: string;
    reportTypeCode?: string;
    shared?: string;
    category?: string;
    sectionCd?: string;
    addReasonCd?: string;
    addTime?: string;
    addUserUid?: number;
    status?: Status;
};

