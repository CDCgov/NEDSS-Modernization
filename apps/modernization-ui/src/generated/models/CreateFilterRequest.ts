/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type CreateFilterRequest = {
    filterCodeUid: number;
    columnUid?: number;
    selectType?: CreateFilterRequest.selectType;
    isRequired: boolean;
};
export namespace CreateFilterRequest {
    export enum selectType {
        SINGLE = 'SINGLE',
        MULTI = 'MULTI',
    }
}

