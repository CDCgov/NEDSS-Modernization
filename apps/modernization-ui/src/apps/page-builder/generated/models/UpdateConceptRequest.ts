/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UpdateConceptRequest = {
    adminComments?: string;
    codeSystem: string;
    conceptCode: string;
    conceptName: string;
    display: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    longName: string;
    preferredConceptName: string;
    status: UpdateConceptRequest.status;
};

export namespace UpdateConceptRequest {

    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }


}

