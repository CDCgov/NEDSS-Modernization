import { useContext, useEffect, useState } from 'react';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { fetchPageSummaries } from './usePageSummaryAPI';
import { ManagePagesTable } from './ManagePagesTable';
import { UserContext } from 'user';

export const ManagePages = () => {
    const [pages, setPages] = useState(null);
    const { sortBy, sortDirection, currentPage, pageSize } = useContext(PagesContext);
    const [searchString] = useState('');
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [isLoading, setIsLoading] = useState(true);
    const [totalElements, setTotalElements] = useState(0);

    useEffect(() => {
        // get Pages
        fetchPageSummaries(token, searchString, sortBy.toLowerCase() + ',' + sortDirection, currentPage, pageSize).then(
            (data: any) => {
                setPages(data.content);
                setTotalElements(data.totalElements);
                setIsLoading(false);
            }
        );
    }, [searchString, currentPage, pageSize, sortBy, sortDirection]);

    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            {pages && !isLoading && (
                                <ManagePagesTable
                                    summaries={pages}
                                    currentPage={currentPage}
                                    pageSize={pageSize}
                                    totalElements={totalElements}
                                />
                            )}
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
