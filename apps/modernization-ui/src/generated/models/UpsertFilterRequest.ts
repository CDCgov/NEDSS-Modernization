/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type UpsertFilterRequest = {
    id?: number;
    filterCodeUid: number;
    columnUid?: number;
    selectType?: UpsertFilterRequest.selectType;
    isRequired: boolean;
};
export namespace UpsertFilterRequest {
    export enum selectType {
        SINGLE = 'SINGLE',
        MULTI = 'MULTI',
    }
}

