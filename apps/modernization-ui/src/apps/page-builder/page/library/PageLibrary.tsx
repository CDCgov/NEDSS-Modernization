import { authorization } from 'authorization';
import { Filter } from 'filters';
import { useState } from 'react';
import { useSorting } from 'sorting';
import { downloadAsCsv } from 'utils/downloadAsCsv';
import { downloadPageLibraryPdf } from 'utils/ExportUtil';

import { usePageLibraryProperties } from './usePageLibraryProperties';
import { usePageSummarySearch } from './usePageSummarySearch';

import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { TableProvider } from 'components/Table/TableProvider';

import { PageControllerService } from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import { PageLibraryTable } from './table/PageLibraryTable';

import { LinkButton } from 'components/button';
import { useConfiguration } from 'configuration';
import styles from './page-library.module.scss';

const PageLibrary = () => {
    return (
        <TableProvider>
            <PageLibraryContent />
        </TableProvider>
    );
};

const PageLibraryContent = () => {
    const { sortBy } = useSorting();
    const config = useConfiguration();
    const { pages, searching, search, filter } = usePageSummarySearch();
    const { properties } = usePageLibraryProperties();

    const [filters, setFilters] = useState<Filter[]>([]);

    const handleFilter = (filters: Filter[]) => {
        setFilters(filters);
        filter(filters);
    };

    return (
        <>
            <CustomFieldAdminBanner />
            <PageBuilder nav>
                <section className={styles.library}>
                    <header>
                        <h2>Page library</h2>
                        {!config.loading && config.features.pageBuilder.page.management.enabled ? (
                            <NavLinkButton to={'/page-builder/pages/add'}>Create new page</NavLinkButton>
                        ) : (
                            <LinkButton type="solid" target="_self" href="/nbs/ManagePage.do?method=addPageLoad">
                                Create new page
                            </LinkButton>
                        )}
                    </header>
                    <PageLibraryMenu
                        properties={properties}
                        filters={filters}
                        onSearch={search}
                        onFilter={handleFilter}
                        onDownload={handleDownloadCSV}
                        onPrint={handleDownloadPDF}
                    />
                    {!config.loading && (
                        <PageLibraryTable
                            enableManagement={config.features.pageBuilder.page.management.enabled}
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

const handleDownloadCSV = () => {
    PageControllerService.downloadPageLibraryUsingGet({ authorization: authorization() }).then((file) =>
        downloadAsCsv({ data: file, fileName: 'PageLibrary.csv', fileType: 'text/csv' })
    );
};

const handleDownloadPDF = () => {
    try {
        downloadPageLibraryPdf(authorization());
    } catch (error) {
        console.log(error);
    }
};

export { PageLibrary };
