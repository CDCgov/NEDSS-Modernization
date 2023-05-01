/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type EventId = {
    id?: string;
    type?: EventId.type;
};

export namespace EventId {

    export enum type {
        ACCESSION_NUMBER = 'ACCESSION_NUMBER',
        LAB_ID = 'LAB_ID',
    }


}

