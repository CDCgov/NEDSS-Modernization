/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type Concept = {
    adminComments?: string;
    codeSetName: string;
    codeSystem: string;
    conceptCode: string;
    conceptName: string;
    display: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    localCode: string;
    longName: string;
    preferredConceptName: string;
    status: Concept.status;
};

export namespace Concept {

    export enum status {
        ACTIVE = 'ACTIVE',
        INACTIVE = 'INACTIVE',
    }


}

