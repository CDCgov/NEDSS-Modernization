/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type InvestigationEventId = {
    id?: string;
    investigationEventType?: InvestigationEventId.investigationEventType;
};

export namespace InvestigationEventId {

    export enum investigationEventType {
        ABCS_CASE_ID = 'ABCS_CASE_ID',
        CITY_COUNTY_CASE_ID = 'CITY_COUNTY_CASE_ID',
        INVESTIGATION_ID = 'INVESTIGATION_ID',
        NOTIFICATION_ID = 'NOTIFICATION_ID',
        STATE_CASE_ID = 'STATE_CASE_ID',
    }


}

