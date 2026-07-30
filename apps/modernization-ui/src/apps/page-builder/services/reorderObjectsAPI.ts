import { logErrorToUserConsole } from 'utils/logging';
import { ReorderControllerService } from '../generated';

export const reorderObjects = (after: number, component: number, page: number): Promise<unknown> => {
    return ReorderControllerService.orderComponentAfter({
        after: after,
        component: component,
        page: page,
    })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            logErrorToUserConsole('ERR', error);
        });
};
