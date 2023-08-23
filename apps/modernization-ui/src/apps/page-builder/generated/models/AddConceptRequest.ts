/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';

export type AddConceptRequest = {
    adminComments?: string;
    code: string;
    displayName: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    messagingInfo: MessagingInfo;
    shortDisplayName: string;
    statusCode: AddConceptRequest.statusCode;
};

export namespace AddConceptRequest {

    export enum statusCode {
        A = 'A',
        I = 'I',
    }


}

