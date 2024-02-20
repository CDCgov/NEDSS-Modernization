/* eslint-disable camelcase */
import { PageRuleControllerService } from 'apps/page-builder/generated';

export const fetchBusinessRules = async (
    authorization: string,
    searchValue: string,
    pageId: number,
    sort: any,
    currentPage: number,
    pageSize: number
) => {
    if (!searchValue) {
        try {
            const response = await PageRuleControllerService.getAllPageRuleUsingGet({
                authorization,
                id: pageId,
                page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
                size: pageSize,
                sort: ''
            });
            return response || [];
        } catch (error: any) {
            console.error(error.body);
        }
    } else {
        const request = {
            searchValue
        };
        try {
            const response_1 = await PageRuleControllerService.findPageRuleUsingPost({
                authorization,
                request,
                id: pageId,
                page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
                size: pageSize,
                sort
            });
            return response_1 || [];
        } catch (error_1: any) {
            console.error(error_1.body);
        }
    }
};
