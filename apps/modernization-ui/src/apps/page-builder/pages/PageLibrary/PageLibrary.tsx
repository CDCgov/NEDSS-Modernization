import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { useContext, useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { UserContext } from 'user';
import { fetchPageSummaries } from '../../services/pagesAPI';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './PageLibrary.scss';
import { ManagePagesTable } from './ManagePagesTable';
import { PageSummary } from 'apps/page-builder/generated';

export const PageLibrary = () => {
    const [pages, setPages] = useState<PageSummary[]>([]);
    const { searchQuery, sortBy, sortDirection, setCurrentPage, currentPage, pageSize, setIsLoading } =
        useContext(PagesContext);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [totalElements, setTotalElements] = useState(0);
    const [searchParams] = useSearchParams();

    useEffect(() => {
        setIsLoading(true);

        // set current page from query param
        if (searchParams.get('page') && parseInt(searchParams.get('page') || '') > 0) {
            const pageFromQuery = searchParams.get('page');
            setCurrentPage(parseInt(pageFromQuery || '') || 1);
        }

        // get Pages
        fetchPageSummaries(token, searchQuery, sortBy.toLowerCase() + ',' + sortDirection, currentPage, pageSize)
            .then((data) => {
                setPages(data.content ?? []);
                setTotalElements(data.totalElements ?? 0);
                setIsLoading(false);
            })
            .catch((error) => {
                console.error(error);
            });
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);

    return (
        <PageBuilder page="manage-pages" menu={true}>
            <div className="manage-pages">
                <div className="manage-pages__container">
                    <div className="manage-pages__table">
                        <ManagePagesTable
                            summaries={pages}
                            currentPage={currentPage}
                            pageSize={pageSize}
                            totalElements={totalElements}
                        />
                    </div>
                </div>
            </div>
        </PageBuilder>
    );
};
