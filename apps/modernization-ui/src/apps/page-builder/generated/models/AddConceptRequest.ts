/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ConceptMessagingInfo } from './ConceptMessagingInfo';

export type AddConceptRequest = {
    adminComments?: string;
    code: string;
    displayName: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    messagingInfo: ConceptMessagingInfo;
    shortDisplayName: string;
    statusCode: AddConceptRequest.statusCode;
};

export namespace AddConceptRequest {

    export enum statusCode {
        A = 'A',
        I = 'I',
    }


}

