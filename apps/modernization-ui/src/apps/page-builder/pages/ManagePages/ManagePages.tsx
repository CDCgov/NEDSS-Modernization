import { useContext, useEffect, useState } from 'react';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { fetchPageSummaries } from '../../services/pagesAPI';
import { ManagePagesTable } from './ManagePagesTable';
import { UserContext } from 'user';

export const ManagePages = () => {
    const [pages, setPages] = useState([]);
    const { searchQuery, sortBy, sortDirection, currentPage, pageSize, setIsLoading } = useContext(PagesContext);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [totalElements, setTotalElements] = useState(0);

    useEffect(() => {
        setIsLoading(true);
        // get Pages
        fetchPageSummaries(token, searchQuery, sortBy.toLowerCase() + ',' + sortDirection, currentPage, pageSize).then(
            (data: any) => {
                setPages(data.content);
                setTotalElements(data.totalElements);
                setIsLoading(false);
            }
        );
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
