/* eslint-disable camelcase */
import { useContext, useEffect, useState } from 'react';
import { ManagePagesTableContainer } from './ManagePagesTableContainer';
import './ManagePages.scss';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { PageProvider } from 'page';
import { PageSummaryControllerService, Page_PageSummary_ } from 'apps/page-builder/generated';
import { UserContext } from 'user';

export const ManagePages = () => {
    const { state } = useContext(UserContext);
    const [pageSize] = useState(10);
    const [summaryResponse, setSummaryResponse] = useState({} as Page_PageSummary_);

    useEffect(() => {
        PageSummaryControllerService.searchUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            request: { search: '' },
            page: 0,
            size: 10
        }).then((response) => setSummaryResponse(response));
    }, []);
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <PageProvider pageSize={pageSize}>
                                <ManagePagesTableContainer pages={summaryResponse.content} />
                            </PageProvider>
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
