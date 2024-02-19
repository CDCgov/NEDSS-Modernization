/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type CreateConceptRequest = {
    adminComments?: string;
    codeSystem: string;
    conceptCode: string;
    conceptName: string;
    display: string;
    effectiveFromTime?: string;
    effectiveToTime?: string;
    localCode: string;
    longName: string;
    preferredConceptName: string;
    status: CreateConceptRequest.status;
};

export namespace CreateConceptRequest {

    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }


}

