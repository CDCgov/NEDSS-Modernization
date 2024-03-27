/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UpdateConceptRequest = {
    longName: string;
    display: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    status: UpdateConceptRequest.status;
    adminComments?: string;
    conceptCode: string;
    conceptName: string;
    preferredConceptName: string;
    codeSystem: string;
};

export namespace UpdateConceptRequest {

    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }


}

