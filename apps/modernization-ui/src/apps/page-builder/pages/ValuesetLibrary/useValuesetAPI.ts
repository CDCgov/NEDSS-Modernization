/* eslint-disable camelcase */
import { ValueSetControllerService, Page_ValueSet_ } from 'apps/page-builder/generated';

export const fetchValueSet = (
    authorization: string,
    search: any,
    sort: any,
    currentPage: number,
    pageSize: number,
    filter: any
) => {
    if (filter?.newestToOldest || (!search && !filter?.questionType)) {
        return ValueSetControllerService.findAllValueSetsUsingGet({
            authorization,
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        }).then((response: Page_ValueSet_) => {
            return response || [];
        });
    } else {
        // if (filter.newestToOldest || (!searchQuery && !filter?.questionType)) {
        return ValueSetControllerService.searchValueSetUsingPost({
            authorization,
            search: search
                ? { valueSetNm: search, valueSetDescription: search, valueSetTypeCd: filter?.questionType }
                : { valueSetTypeCd: filter?.questionType || 'LOCAL' },
            page: currentPage && currentPage > 1 ? currentPage - 1 : 0,
            size: pageSize,
            sort
        }).then((response: Page_ValueSet_) => {
            return response || [];
        });
    }
};
