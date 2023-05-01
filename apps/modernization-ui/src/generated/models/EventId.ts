/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type EventId = {
    labEventId?: string;
    labEventType?: EventId.labEventType;
};

export namespace EventId {

    export enum labEventType {
        ACCESSION_NUMBER = 'ACCESSION_NUMBER',
        LAB_ID = 'LAB_ID',
    }


}

