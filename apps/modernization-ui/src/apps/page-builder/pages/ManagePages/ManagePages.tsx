import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { useContext, useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { UserContext } from 'user';
import { fetchPageSummaries } from '../../services/pagesAPI';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { ManagePagesTable } from './ManagePagesTable';

export const ManagePages = () => {
    const [pages, setPages] = useState([]);
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
        try {
            fetchPageSummaries(
                token,
                searchQuery,
                sortBy.toLowerCase() + ',' + sortDirection,
                currentPage,
                pageSize
            ).then((data: any) => {
                setPages(data.content);
                setTotalElements(data.totalElements);
                setIsLoading(false);
            });
        } catch (error) {
            console.log(error);
        }
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);

    const legacyCustomFieldsAlert = () => {
        return (
            <AlertBanner type="info">
                <span style={{ marginRight: '4px' }}>To access the legacy Custom Fields Admin menu,</span>{' '}
                <a href="/nbs/LDFAdminLoad.do">click here</a>.
            </AlertBanner>
        );
    };

    return (
        <PageBuilder page="manage-pages" menu={true} header={legacyCustomFieldsAlert()}>
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
