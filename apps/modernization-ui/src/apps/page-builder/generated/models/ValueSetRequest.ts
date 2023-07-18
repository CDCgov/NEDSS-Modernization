/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CodeSetGroupMetadatum } from './CodeSetGroupMetadatum';
import type { CreateCodedValue } from './CreateCodedValue';

export type ValueSetRequest = {
    addTime?: string;
    addUserId?: number;
    adminComments?: string;
    assigningAuthorityCd?: string;
    assigningAuthorityDescTxt?: string;
    codeSetDescTxt?: string;
    codeSetGroup?: CodeSetGroupMetadatum;
    effectiveFromTime?: string;
    effectiveToTime?: string;
    isModifiableInd?: string;
    ldfPicklistIndCd?: string;
    nbsUid?: number;
    parentIsCd?: number;
    sourceDomainNm?: string;
    sourceVersionTxt?: string;
    statusCd?: string;
    statusToTime?: string;
    valueSetCode?: string;
    valueSetNm?: string;
    valueSetOid?: string;
    valueSetStatusCd?: string;
    valueSetStatusTime?: string;
    valueSetTypeCd?: string;
    values?: Array<CreateCodedValue>;
};

