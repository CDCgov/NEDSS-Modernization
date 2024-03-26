/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type PagesQuestion = {
    id: number;
    isStandardNnd?: boolean;
    isStandard?: boolean;
    standard?: string;
    question?: string;
    name: string;
    order: number;
    questionGroupSeq?: number;
    subGroup?: string;
    description?: string;
    coInfection?: boolean;
    dataType?: string;
    mask?: string;
    allowFutureDates?: boolean;
    tooltip?: string;
    visible?: boolean;
    enabled?: boolean;
    required?: boolean;
    defaultValue?: string;
    valueSet?: string;
    displayComponent?: number;
    adminComments?: string;
    fieldLength?: string;
    defaultRdbTableName?: string;
    rdbColumnName?: string;
    defaultLabelInReport?: string;
    dataMartColumnName?: string;
    isPublished?: boolean;
    blockName?: string;
    dataMartRepeatNumber?: number;
    appearsInBatch?: boolean;
    batchLabel?: string;
    batchWidth?: number;
    componentBehavior?: string;
    componentName?: string;
    classCode?: string;
};

