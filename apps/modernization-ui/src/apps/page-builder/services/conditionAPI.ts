/* eslint-disable camelcase */
import { ConditionControllerService, Page_Condition_, ReadConditionRequest } from '../generated';

export const fetchConditions = (
    token: string,
    search?: ReadConditionRequest,
    page?: number,
    size?: number,
    sort?: string
): Promise<Page_Condition_> => {
    return ConditionControllerService.searchConditionsUsingPost({
        authorization: token,
        search: search ?? {},
        page: page && page > 1 ? page - 1 : 0,
        size: size,
        sort
    }).then((response: Page_Condition_) => {
        return response;
    });
};
