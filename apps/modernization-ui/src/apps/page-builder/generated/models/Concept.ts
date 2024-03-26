/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type Concept = {
    codeSetName: string;
    localCode: string;
    longName: string;
    display: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    status: Concept.status;
    adminComments?: string;
    conceptCode: string;
    conceptName: string;
    preferredConceptName: string;
    codeSystem: string;
};

export namespace Concept {

    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }


}

