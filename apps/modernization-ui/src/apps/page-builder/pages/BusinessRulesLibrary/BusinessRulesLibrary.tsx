import { useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { Breadcrumb } from 'breadcrumb';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { Status, usePage } from 'page';
import { BusinessRuleSort, useFetchPageRules } from 'apps/page-builder/hooks/api/useFetchPageRules';
import { useAlert } from 'alert';

export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { search, response, error, isLoading } = useFetchPageRules();
    const { page: curPage, ready, firstPage, reload } = usePage();
    const [sort, setSort] = useState<BusinessRuleSort | undefined>(undefined);
    const [query, setQuery] = useState<string>('');
    const { showAlert } = useAlert();

    const { page } = useGetPageDetails();

    useEffect(() => {
        search({ sort: undefined, page: 0, pageSize: 10 });
    }, [page?.id]);

    useEffect(() => {
        if (curPage.status === Status.Requested && page?.id) {
            search({
                pageId: page.id,
                page: curPage.current - 1,
                pageSize: curPage.pageSize,
                sort: sort,
                query: query
            });
        }
    }, [curPage.status, page?.id]);

    useEffect(() => {
        if (curPage.current === 1) {
            reload();
        } else {
            firstPage();
        }
    }, [query, sort]);

    useEffect(() => {
        if (response) {
            const currentPage = response?.number ? response?.number + 1 : 1;
            ready(response?.totalElements ?? 0, currentPage);
        } else if (error) {
            showAlert({ message: error, type: 'error' });
        }
    }, [response, error]);

    return (
        <>
            <header className="business-rule-header">
                <h2>Page builder</h2>
            </header>
            <div className="business-rules-library padding-top-3">
                {page && (
                    <div className="padding-left-3">
                        <Breadcrumb
                            start="../.."
                            currentPage="Business rules"
                            crumbs={[{ name: page.name, position: 1, to: '..' }]}>
                            Page library
                        </Breadcrumb>
                    </div>
                )}
                <div className="business-rules-library__container">
                    <div className="business-rules-library__table">
                        <BusinessRulesLibraryTable
                            summaries={response?.content ?? []}
                            qtnModalRef={modalRef}
                            onSortChange={setSort}
                            onQueryChange={setQuery}
                            isLoading={isLoading}
                        />
                    </div>
                </div>
            </div>
        </>
    );
};
