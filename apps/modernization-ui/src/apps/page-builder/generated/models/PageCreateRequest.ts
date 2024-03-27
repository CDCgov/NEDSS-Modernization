/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type PageCreateRequest = {
    eventType: string;
    conditionIds: Array<string>;
    name: string;
    templateId: number;
    messageMappingGuide: string;
    pageDescription?: string;
    dataMartName?: string;
};

