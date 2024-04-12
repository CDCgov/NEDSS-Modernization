import { ReorderControllerService } from '../generated';

export const reorderObjects = (after: number, component: number, page: number): Promise<any> => {
    return ReorderControllerService.orderComponentAfter({
        after: after,
        component: component,
        page: page
    })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.log('ERR', error);
        });
};
