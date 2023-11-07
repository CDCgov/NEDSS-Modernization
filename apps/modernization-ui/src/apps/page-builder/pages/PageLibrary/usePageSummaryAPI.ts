/* eslint-disable camelcase */
import { PageControllerService, Page_PageSummary_ } from 'apps/page-builder/generated';

export const fetchPageSummaries = async (
    token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<Page_PageSummary_> => {
    const response = await PageControllerService.searchUsingPost({
        authorization: token,
        request: { search },
        page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
        size: pageSize,
        sort
    });
    return response;
};

export const fetchSinglePageSummary = {};
