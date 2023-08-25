/* eslint-disable camelcase */
import { Condition, ConditionControllerService, CreateConditionRequest } from '../generated';

export const fetchConditions = (token: string): Promise<Array<Condition>> => {
    return ConditionControllerService.findAllConditionsUsingGet({
        authorization: token
    }).then((response) => {
        return response;
    });
};

export const createCondition = (token: string, request: CreateConditionRequest) => {
    return ConditionControllerService.createConditionUsingPost({
        authorization: token,
        request: request
    }).then((response: any) => {
        return response;
    });
};
