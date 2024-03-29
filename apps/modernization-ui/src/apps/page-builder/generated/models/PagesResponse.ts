/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BusinessRule } from './BusinessRule';
import type { PagesTab } from './PagesTab';
export type PagesResponse = {
    id: number;
    name: string;
    status: string;
    description?: string;
    root?: number;
    tabs?: Array<PagesTab>;
    rules?: Array<BusinessRule>;
};

