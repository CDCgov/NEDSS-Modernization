/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type EventDate = {
    from?: string;
    to?: string;
    type?: EventDate.type;
};

export namespace EventDate {

    export enum type {
        DATE_OF_REPORT = 'DATE_OF_REPORT',
        INVESTIGATION_CLOSED_DATE = 'INVESTIGATION_CLOSED_DATE',
        INVESTIGATION_CREATE_DATE = 'INVESTIGATION_CREATE_DATE',
        INVESTIGATION_START_DATE = 'INVESTIGATION_START_DATE',
        LAST_UPDATE_DATE = 'LAST_UPDATE_DATE',
        NOTIFICATION_CREATE_DATE = 'NOTIFICATION_CREATE_DATE',
    }


}

