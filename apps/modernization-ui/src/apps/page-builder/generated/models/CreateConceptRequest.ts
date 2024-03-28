/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type CreateConceptRequest = {
    localCode: string;
    longName: string;
    display: string;
    effectiveFromTime?: string;
    effectiveToTime?: string;
    status: CreateConceptRequest.status;
    adminComments?: string;
    conceptCode: string;
    conceptName: string;
    preferredConceptName: string;
    codeSystem: string;
};
export namespace CreateConceptRequest {
    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }
}

