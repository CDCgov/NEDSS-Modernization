/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type LaboratoryEventDateSearch = {
    eventDateType?: LaboratoryEventDateSearch.eventDateType;
    from?: string;
    to?: string;
};

export namespace LaboratoryEventDateSearch {

    export enum eventDateType {
        DATE_OF_REPORT = 'DATE_OF_REPORT',
        DATE_OF_SPECIMEN_COLLECTION = 'DATE_OF_SPECIMEN_COLLECTION',
        DATE_RECEIVED_BY_PUBLIC_HEALTH = 'DATE_RECEIVED_BY_PUBLIC_HEALTH',
        LAB_REPORT_CREATE_DATE = 'LAB_REPORT_CREATE_DATE',
        LAST_UPDATE_DATE = 'LAST_UPDATE_DATE',
    }


}

