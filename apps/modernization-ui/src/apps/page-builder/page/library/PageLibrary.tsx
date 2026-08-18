import { useState } from 'react';

import fileDownload from 'js-file-download';

import {
    Date,
    DateRange,
    MultiValue,
    PageSummaryDownloadControllerService,
    SingleValue,
} from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { TableProvider } from 'components/Table/TableProvider';
import { useConfiguration } from 'configuration';
import { LinkButton } from 'design-system/button';
import { NavLinkButton } from 'design-system/button';
import { externalize, Filter } from 'filters';
import { useSorting } from 'libs/sorting';
import { downloadPageLibraryPdf } from 'utils/ExportUtil';

import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import styles from './page-library.module.scss';
import { PageLibraryTable } from './table/PageLibraryTable';
import { usePageLibraryProperties } from './usePageLibraryProperties';
import { usePageSummarySearch } from './usePageSummarySearch';

const PageLibrary = () => {
    return (
        <TableProvider>
            <PageLibraryContent />
        </TableProvider>
    );
};

type ApiFilter = Array<Date | DateRange | MultiValue | SingleValue>;

const PageLibraryContent = () => {
    const { sorting, sortBy } = useSorting();
    const config = useConfiguration();
    const { keyword, pages, searching, search } = usePageSummarySearch();
    const { properties } = usePageLibraryProperties();

    const [filters, setFilters] = useState<Filter[]>([]);

    const handleFilter = (filters: Filter[]) => {
        setFilters(filters);
        search(keyword, filters);
    };

    const handleSearch = (query?: string) => {
        search(query, filters);
    };

    const handleDownloadCSV = () => {
        PageSummaryDownloadControllerService.csv({
            requestBody: {
                search: keyword,
                filters: externalize(filters) as ApiFilter,
            },
            sort: sorting ? [sorting] : ['id,asc'],
        }).then((file) => fileDownload(file, 'PageLibrary.csv', 'text/csv'));
    };

    const handleDownloadPDF = () => {
        downloadPageLibraryPdf(keyword ?? '', filters, sorting);
    };

    return (
        <>
            <CustomFieldAdminBanner />
            <PageBuilder nav={true}>
                <section className={styles.library} id="pageLibrary">
                    <header>
                        <h1 aria-label="Page library">Page library</h1>
                        {config.features.pageBuilder.page.management.create.enabled ? (
                            <NavLinkButton className="createNewPageButton" to="/page-builder/pages/add">
                                Create new page
                            </NavLinkButton>
                        ) : (
                            <LinkButton target="_self" href="/nbs/page-builder/api/v1/pages/create">
                                Create new page
                            </LinkButton>
                        )}
                    </header>
                    <PageLibraryMenu
                        properties={properties}
                        filters={filters}
                        onSearch={handleSearch}
                        onFilter={handleFilter}
                        onDownloadCsv={handleDownloadCSV}
                        onDownloadPdf={handleDownloadPDF}
                    />
                    <PageLibraryTable
                        enableEdit={config.features.pageBuilder.page.management.edit.enabled}
                        summaries={pages}
                        searching={searching}
                        onSort={sortBy}
                    />
                </section>
            </PageBuilder>
        </>
    );
};

export { PageLibrary };
