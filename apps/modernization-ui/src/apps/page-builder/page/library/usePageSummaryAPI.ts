import { PageSummaryService, PagePageSummary } from 'apps/page-builder/generated';

export const fetchPageSummaries = async (
    token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<PagePageSummary> =>
    PageSummaryService.search({
        requestBody: {
            request: { search },
            pageable: {
                page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
                size: pageSize,
                sort: sort ? [sort] : undefined
            }
        }
    });

export const fetchSinglePageSummary = {};
