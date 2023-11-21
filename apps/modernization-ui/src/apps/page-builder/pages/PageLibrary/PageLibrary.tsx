import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';
import { fetchPageSummaries } from '../../services/pagesAPI';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './PageLibrary.scss';
import { ManagePagesTable } from './ManagePagesTable';
import { PageSummary } from 'apps/page-builder/generated';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';

export const PageLibrary = () => {
    const [pages, setPages] = useState<PageSummary[]>([]);
    const { searchQuery, sortBy, sortDirection, currentPage, pageSize, setIsLoading } = useContext(PagesContext);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [totalElements, setTotalElements] = useState(0);

    useEffect(() => {
        setIsLoading(true);

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
        <>
            <CustomFieldAdminBanner />
            <PageBuilder nav>
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
        </>
    );
};
