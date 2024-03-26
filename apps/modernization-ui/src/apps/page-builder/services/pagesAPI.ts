/* eslint-disable camelcase */
import { PageControllerService, PagePageSummary, PageSummaryService, PagesService } from 'apps/page-builder/generated';

export const fetchPageSummaries = (
    token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<PagePageSummary> => {
    return PageSummaryService.search({
        requestBody: {
            request: { search },
            pageable: {
                page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
                size: pageSize,
                sort: sort ? [sort] : undefined
            }
        }
    });
};

export const fetchPageDetails = (id: number) => {
    return PagesService.details({
        id: id
    });
};

export const savePageAsDraft = (id: number) => {
    return PageControllerService.savePageDraft({
        id: id
    }).then((response) => {
        return response;
    });
};
