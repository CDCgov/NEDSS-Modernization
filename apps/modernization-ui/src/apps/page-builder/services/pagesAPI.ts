/* eslint-disable camelcase */
import { PageControllerService, Page_PageSummary_ } from 'apps/page-builder/generated';

export const fetchPageSummaries = (
    token: string,
    search?: string,
    sort?: string,
    currentPage?: number,
    pageSize?: number
): Promise<Page_PageSummary_> => {
    return PageControllerService.searchUsingPost({
        authorization: token,
        request: { search },
        page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
        size: pageSize,
        sort
    }).then((response: Page_PageSummary_) => {
        console.log('Pages', response);
        return response;
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
    console.log(token, conditionIds, eventType, messageMappingGuide, name, pageDescription, templateId, dataMartName);
    return PageControllerService.createPageUsingPost({
        authorization: token,
        request: { conditionIds, dataMartName, eventType, messageMappingGuide, name, pageDescription, templateId }
    }).then((response: any) => {
        console.log('RESPONSE', response);
        return response;
    });
};
