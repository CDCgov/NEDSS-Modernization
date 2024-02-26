/* eslint-disable camelcase */
import { PageRuleControllerService } from 'apps/page-builder/generated';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { authorization } from 'authorization';

export const fetchBusinessRules = async (
    searchValue?: string,
    sort?: any,
    currentPage?: number,
    pageSize?: number
) => {
    const {page} = useGetPageDetails();

    if (!searchValue) {
        try {
            const response = await PageRuleControllerService.getAllPageRuleUsingGet({
                authorization: authorization(),
                id: page?.id ?? 0,
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
                authorization: authorization(),
                request,
                id: page?.id ?? 0,
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
