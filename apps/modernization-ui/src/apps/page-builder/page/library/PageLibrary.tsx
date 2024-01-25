import { authorization } from 'authorization';
import { externalize, Filter } from 'filters';
import { useState } from 'react';
import { useSorting } from 'sorting';
import { usePageLibraryProperties } from './usePageLibraryProperties';
import { usePageSummarySearch } from './usePageSummarySearch';

import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { TableProvider } from 'components/Table/TableProvider';

import { PageSummaryDownloadControllerService } from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import { PageLibraryTable } from './table/PageLibraryTable';

import { LinkButton } from 'components/button';
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
        PageSummaryDownloadControllerService.csvUsingPost({
            authorization: authorization(),
            sort: sorting ?? 'id,asc',
            request: {
                search: keyword,
                filters: externalize(filters)
            }
        }).then((file) => download({ data: file, fileName: 'PageLibrary.csv', fileType: 'text/csv' }));
    };

    const handleDownloadPDF = () => {
        downloadPageLibraryPdf(authorization(), keyword ?? '', filters, sorting);
    };

    return (
        <>
            <CustomFieldAdminBanner />
            <PageBuilder nav>
                <section className={styles.library}>
                    <header>
                        <h2 aria-label="Page library">Page library</h2>
                        {!config.loading && config.features.pageBuilder.page.management.create.enabled ? (
                            <NavLinkButton to={'/page-builder/pages/add'}>Create new page</NavLinkButton>
                        ) : (
                            <LinkButton type="solid" target="_self" href="/nbs/page-builder/api/v1/pages/create">
                                Create new page
                            </LinkButton>
                        )}
                    </header>
                    <PageLibraryMenu
                        properties={properties}
                        filters={filters}
                        onSearch={handleSearch}
                        onFilter={handleFilter}
                        onDownload={handleDownloadCSV}
                        onPrint={handleDownloadPDF}
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
