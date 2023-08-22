/* eslint-disable camelcase */
import { Condition, ConditionControllerService } from '../generated';

export const fetchConditions = (token: string): Promise<Array<Condition>> => {
    return ConditionControllerService.findAllConditionsUsingGet({
        authorization: token
    }).then((response) => {
        return response;
    });
};
