/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Codeset } from './Codeset';

export type CodeSetGroupMetadatum = {
    codeSetDescTxt?: string;
    codeSetNm?: string;
    codeSetShortDescTxt?: string;
    codesets?: Array<Codeset>;
    id?: number;
    ldfPicklistIndCd?: string;
    phinStdValInd?: string;
    vadsValueSetCode?: string;
};

