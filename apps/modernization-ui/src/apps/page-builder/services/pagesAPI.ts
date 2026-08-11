import { PageControllerService, PagePageSummary, PagesService, PageSummaryService } from 'apps/page-builder/generated';

export const fetchPageSummaries = (
    _token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<PagePageSummary> => {
    return PageSummaryService.search({
        requestBody: {
            search,
        },
        page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
        size: pageSize,
        sort: sort ? [sort] : undefined,
    });
};

export const fetchPageDetails = (id: number) => {
    return PagesService.details({
        id,
    });
};

export const savePageAsDraft = (id: number) => {
    return PageControllerService.savePageDraft({
        id,
    }).then((response) => {
        return response;
    });
};
