/* eslint-disable camelcase */
import { ValueSetControllerService, Page_ValueSet_, ValueSet } from 'apps/page-builder/generated';
import { Status, usePage } from 'page';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';

export const useValuesetAPI = (search?: string, sort?: string) => {
    const [valueSet, setValueSet] = useState([] as ValueSet[]);
    const { page, firstPage, ready } = usePage();
    const { state } = useContext(UserContext);

    const fetchValueSet = () => {
        setValueSet([]);
        ValueSetControllerService.searchValueSetUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            search: { valueSetTypeCd: search },
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

    useEffect(() => {
        if (page.status === Status.Requested) {
            if (search === 'RECENT') {
                fetchRecentValueSet();
            } else {
                fetchValueSet();
            }
        }
    }, [page]);

    useEffect(() => {
        firstPage();
    }, [search, sort]);

    return valueSet;
};
