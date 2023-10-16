/* eslint-disable camelcase */
import { PageRuleControllerService } from 'apps/page-builder/generated';

export const fetchBusinessRules = (
    authorization: string,
    search: any,
    sort: any,
    currentPage: number,
    pageSize: number
) => {
    return PageRuleControllerService.findAllRuleResponseUsingGet({
        authorization,
        page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
        size: pageSize,
        sort
    }).then((response: any) => {
        return response || [];
    });
};
