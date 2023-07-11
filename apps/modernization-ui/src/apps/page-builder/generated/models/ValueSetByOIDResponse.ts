/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Status } from './Status';
import type { ValueSetByOIDResults } from './ValueSetByOIDResults';

export type ValueSetByOIDResponse = {
    data?: ValueSetByOIDResults;
    status?: Status;
};

