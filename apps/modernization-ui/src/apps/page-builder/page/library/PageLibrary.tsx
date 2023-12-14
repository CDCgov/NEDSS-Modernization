import { useState } from 'react';
import { authorization } from 'authorization';
import { useSorting } from 'sorting';
import { Filter } from 'filters';
import { downloadAsCsv } from 'utils/downloadAsCsv';
import { downloadPageLibraryPdf } from 'utils/ExportUtil';

import { usePageSummarySearch } from './usePageSummarySearch';
import { usePageLibraryProperties } from './usePageLibraryProperties';

import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { TableProvider } from 'components/Table/TableProvider';

import { PageControllerService } from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import { PageLibraryTable } from './table/PageLibraryTable';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';

import styles from './page-library.module.scss';
import { useConfiguration } from 'configuration';
import { LinkButton } from 'components/button';

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
                        {config.features.pageBuilder.page.management.enabled ? (
                            <NavLinkButton to={'/page-builder/add/page'}>Create new page</NavLinkButton>
                        ) : (
                            <LinkButton href="/nbs/ManagePage.do?method=addPageLoad">Create new page</LinkButton>
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
                    <PageLibraryTable summaries={pages} searching={searching} onSort={sortBy} />
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
