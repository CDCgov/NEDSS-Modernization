/* eslint-disable camelcase */
import {
    Condition,
    ConditionControllerService,
    CreateConditionRequest,
    PageCondition,
    ReadConditionRequest
} from '../generated';

export const fetchConditions = async (): Promise<Array<Condition>> => {
    const response = await ConditionControllerService.findAllConditions();
    return response;
};

export const findConditions = async (page: number, size: number, sort: string) => {
    const response = await ConditionControllerService.findConditions({
        page,
        size,
        sort: sort ? [sort] : undefined
    });
    return response;
};

export const searchConditions = async (
    token: string,
    page: number,
    size: number,
    sort: string,
    search: ReadConditionRequest
): Promise<PageCondition> => {
    const response = await ConditionControllerService.searchConditions({
        requestBody: search,
        page,
        size,
        sort: sort ? [sort] : undefined
    });
    return response;
};

export const createCondition = (request: CreateConditionRequest) => {
    return ConditionControllerService.createCondition({
        requestBody: request
    }).then((response) => {
        return response;
    });
};
