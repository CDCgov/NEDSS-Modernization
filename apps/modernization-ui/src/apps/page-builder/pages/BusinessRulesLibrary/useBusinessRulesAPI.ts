/* eslint-disable camelcase */
import { PageRuleControllerService, Page_Rule_ } from 'apps/page-builder/generated';

export const fetchBusinessRules = (
    authorization: string,
    searchValue: string,
    pageId: number,
    sort: any,
    currentPage: number,
    pageSize: number
) => {
    if (!searchValue) {
        return PageRuleControllerService.getAllPageRuleUsingGet({
            authorization,
            id: pageId,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        })
            .then((response: any) => {
                return response || [];
            })
            .catch((error) => {
                console.error(error.body);
            });
    } else {
        const request = {
            searchValue
        };
        return PageRuleControllerService.findPageRuleUsingPost({
            authorization,
            request,
            id: pageId,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        })
            .then((response: Page_Rule_) => {
                return response || [];
            })
            .catch((error) => {
                console.error(error.body);
            });
    }
};
