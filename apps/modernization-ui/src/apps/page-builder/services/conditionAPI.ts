/* eslint-disable camelcase */
import {
    Condition,
    ConditionControllerService,
    CreateConditionRequest,
    Page_Condition_,
    ReadConditionRequest
} from '../generated';

export const fetchConditions = async (token: string): Promise<Array<Condition>> => {
    const response = await ConditionControllerService.findAllConditionsUsingGet({
        authorization: token
    });
    return response;
};

export const findConditions = async (token: string, page: number, size: number, sort: string) => {
    const response = await ConditionControllerService.findConditionsUsingGet({
        authorization: token,
        page,
        size,
        sort
    });
    return response;
};

export const searchConditions = async (
    token: string,
    page: number,
    size: number,
    sort: string,
    search: ReadConditionRequest
): Promise<Page_Condition_> => {
    const response = await ConditionControllerService.searchConditionsUsingPost({
        authorization: token,
        search,
        page,
        size,
        sort
    });
    return response;
};

export const createCondition = (token: string, request: CreateConditionRequest) => {
    return ConditionControllerService.createConditionUsingPost({
        authorization: token,
        request: request
    }).then((response: any) => {
        return response;
    });
};
