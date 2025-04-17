import { useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { Breadcrumb } from 'breadcrumb';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { Status, usePagination } from 'pagination';
import { BusinessRuleSort, RuleSortField, useFetchPageRules } from 'apps/page-builder/hooks/api/useFetchPageRules';
import { useAlert } from 'alert';
import { useDownloadPageLibrary } from 'apps/page-builder/hooks/api/useDownloadPageLibrary';
import { Direction } from '../../../../sorting';

export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { search, response, error, isLoading } = useFetchPageRules();
    const { page: curPage, ready, firstPage, reload } = usePagination();
    const [sort, setSort] = useState<BusinessRuleSort | undefined>({
        field: RuleSortField.FUNCTION,
        direction: Direction.Descending
    });
    const [query, setQuery] = useState<string>('');
    const { showAlert } = useAlert();
    const { downloadCsv, downloadPdf } = useDownloadPageLibrary();

    const { page } = useGetPageDetails();

    useEffect(() => {
        search({ sort, page: 0, pageSize: 10 });
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

    const handleCsvDownload = () => {
        if (page?.id) {
            downloadCsv(page.id, sort, query);
        }
    };

    const handlePdfDownload = () => {
        if (page?.id) {
            downloadPdf(page.id, sort, query);
        }
    };

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
                            rules={response?.content ?? []}
                            qtnModalRef={modalRef}
                            onSortChange={setSort}
                            onQueryChange={setQuery}
                            isLoading={isLoading}
                            onDownloadCsv={handleCsvDownload}
                            onDownloadPdf={handlePdfDownload}
                        />
                    </div>
                </div>
            </div>
        </>
    );
};
