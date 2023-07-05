/* eslint-disable camelcase */
import { PageSummary, PageSummaryControllerService, Page_PageSummary_ } from 'apps/page-builder/generated';
import { Status, usePage } from 'page';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';

export const usePageSummaryAPI = (search?: string, sort?: string) => {
    const [pageSummaries, setPageSummaries] = useState([] as PageSummary[]);
    const { page, firstPage, ready } = usePage();
    const { state } = useContext(UserContext);

    const fetchPageSummaries = () => {
        PageSummaryControllerService.searchUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            request: { search },
            page: page.current - 1,
            size: page.pageSize,
            sort
        }).then((response: Page_PageSummary_) => {
            setPageSummaries(response.content || []);
            ready(response.totalElements || 0, page.current);
        });
    };

    useEffect(() => {
        if (page.status === Status.Requested) {
            fetchPageSummaries();
        }
    }, [page]);

    useEffect(() => {
        firstPage();
    }, [search, sort]);

    return pageSummaries;
};
