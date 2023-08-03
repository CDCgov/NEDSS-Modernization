/* eslint-disable camelcase */
import { ValueSetControllerService, Page_ValueSet_, ValueSet } from 'apps/page-builder/generated';
import { Status, usePage } from 'page';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';

export const useValuesetAPI = (search?: string, sort?: string, filter?: any) => {
    const [valueSet, setValueSet] = useState([] as ValueSet[]);
    const { page, firstPage, ready } = usePage();
    const { state } = useContext(UserContext);

    const fetchValueSet = () => {
        setValueSet([]);

        ValueSetControllerService.searchValueSetUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            search: search
                ? { valueSetNm: search, valueSetDescription: search, valueSetTypeCd: filter?.questionType }
                : { valueSetTypeCd: filter?.questionType },
            page: page.current - 1,
            size: page.pageSize,
            sort
        }).then((response: Page_ValueSet_) => {
            setValueSet(response.content || []);
            ready(response.totalElements || 0, page.current);
        });
    };
    const fetchRecentValueSet = () => {
        ValueSetControllerService.findAllValueSetsUsingGet({
            authorization: `Bearer ${state.getToken()}`,
            page: page.current - 1,
            size: page.pageSize,
            sort
        }).then((response: Page_ValueSet_) => {
            setValueSet(response.content || []);
            ready(response.totalElements || 0, page.current);
        });
    };
    console.log(
        'filter.newestToOldest || (!search && !filter?.questionType)...',
        filter.newestToOldest,
        !search && !filter?.questionType
    );
    useEffect(() => {
        if (page.status === Status.Requested) {
            if (filter.newestToOldest || (!search && !filter?.questionType)) {
                fetchRecentValueSet();
            } else {
                fetchValueSet();
            }
        }
    }, [page]);

    useEffect(() => {
        firstPage();
    }, [search, sort, filter]);

    return valueSet;
};
