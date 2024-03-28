/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type QuestionValidationRequest = {
    field: QuestionValidationRequest.field;
    value: string;
};
export namespace QuestionValidationRequest {
    export enum field {
        UNIQUE_ID = 'UNIQUE_ID',
        UNIQUE_NAME = 'UNIQUE_NAME',
        RDB_COLUMN_NAME = 'RDB_COLUMN_NAME',
        DATA_MART_COLUMN_NAME = 'DATA_MART_COLUMN_NAME',
    }
}

