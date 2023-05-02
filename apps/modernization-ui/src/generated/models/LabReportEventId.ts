/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type LabReportEventId = {
    labEventId?: string;
    labEventType?: LabReportEventId.labEventType;
};

export namespace LabReportEventId {

    export enum labEventType {
        ACCESSION_NUMBER = 'ACCESSION_NUMBER',
        LAB_ID = 'LAB_ID',
    }


}

