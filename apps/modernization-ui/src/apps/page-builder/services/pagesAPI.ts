/* eslint-disable camelcase */
import {
    PageControllerService,
    Page_PageSummary_,
    PagesService,
    PageSummaryService
} from 'apps/page-builder/generated';

export const fetchPageSummaries = (
    token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<Page_PageSummary_> => {
    return PageSummaryService.search({
        authorization: token,
        request: { search },
        page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
        size: pageSize,
        sort
    });
};

export const createPage = (
    token: string,
    conditionIds: string[],
    eventType: string,
    messageMappingGuide: string,
    name: string,
    templateId: number,
    pageDescription?: string,
    dataMartName?: string
) => {
    return PageControllerService.createPageUsingPost({
        authorization: token,
        request: { conditionIds, dataMartName, eventType, messageMappingGuide, name, pageDescription, templateId }
    }).then((response: any) => {
        return response;
    });
};

export const fetchPageDetails = (token: string, id: number) => {
    return PagesService.detailsUsingGet({
        authorization: token,
        id: id
    });
};

export const savePageAsDraft = (token: string, id: number) => {
    return PageControllerService.savePageDraftUsingPut({
        authorization: token,
        id: id
    }).then((response: any) => {
        return response;
    });
};
