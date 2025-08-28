import { externalize, Filter } from 'filters';
import { useState } from 'react';
import { useSorting } from 'libs/sorting';
import { usePageLibraryProperties } from './usePageLibraryProperties';
import { usePageSummarySearch } from './usePageSummarySearch';

import { NavLinkButton } from 'design-system/button';
import { TableProvider } from 'components/Table/TableProvider';

import {
    PageSummaryDownloadControllerService,
    Date,
    DateRange,
    MultiValue,
    SingleValue
} from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import { PageLibraryTable } from './table/PageLibraryTable';

import { LinkButton } from 'design-system/button';
import { useConfiguration } from 'configuration';
import styles from './page-library.module.scss';
import { downloadPageLibraryPdf } from 'utils/ExportUtil';
import { download } from 'utils/download';

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
                filters: externalize(filters) as ApiFilter
            },
            sort: sorting ? [sorting] : ['id,asc']
        }).then((file) => download({ data: file, fileName: 'PageLibrary.csv', fileType: 'text/csv' }));
    };

    const handleDownloadPDF = () => {
        downloadPageLibraryPdf(keyword ?? '', filters, sorting);
    };

    return (
        <>
            <CustomFieldAdminBanner />
            <PageBuilder nav>
                <section className={styles.library} id="pageLibrary">
                    <header>
                        <h1 aria-label="Page library">Page library</h1>
                        {!config.loading && config.features.pageBuilder.page.management.create.enabled ? (
                            <NavLinkButton className="createNewPageButton" to={'/page-builder/pages/add'}>
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
                    {!config.loading && (
                        <PageLibraryTable
                            enableEdit={config.features.pageBuilder.page.management.edit.enabled}
                            summaries={pages}
                            searching={searching}
                            onSort={sortBy}
                        />
                    )}
                </section>
            </PageBuilder>
        </>
    );
};

export { PageLibrary };
