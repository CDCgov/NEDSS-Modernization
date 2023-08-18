/* eslint-disable camelcase */
import { ConditionControllerService, ReadConditionRequest } from '../generated';
import { Condition_Summaries_ } from '../generated/models/Condition_Summaries';

export const fetchConditions = (
    token: string,
    search?: ReadConditionRequest,
    page?: number,
    size?: number,
    sort?: string
): Promise<Condition_Summaries_> => {
    return ConditionControllerService.searchConditionsUsingPost({
        authorization: token,
        search: search ? search : {},
        page: page && page > 1 ? page - 1 : 0,
        size: size,
        sort
    }).then((response: Condition_Summaries_) => {
        return response;
    });
};
