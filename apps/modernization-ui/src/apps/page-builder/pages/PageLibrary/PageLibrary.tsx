import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@trussworks/react-uswds';
import { authorization } from 'authorization';
import { PageProvider } from 'page';
import { SortingProvider, useSorting } from 'sorting';
import { Filter } from 'filters';
import { downloadAsCsv } from 'utils/downloadAsCsv';
import { downloadPageLibraryPdf } from 'utils/ExportUtil';

import { usePageSummarySearch } from './usePageSummarySearch';
import { usePageLibraryProperties } from './usePageLibraryProperties';

import { PageControllerService } from 'apps/page-builder/generated';
import { PageBuilder } from 'apps/page-builder/pages/PageBuilder/PageBuilder';
import { PageLibraryMenu } from './menu/PageLibraryMenu';
import { PageLibraryTable } from './table/PageLibraryTable';
import { CustomFieldAdminBanner } from './CustomFieldAdminBanner';

import styles from './page-library.module.scss';

type PageLibraryProps = {
    pageSize?: number;
};

const PageLibrary = (props?: PageLibraryProps) => {
    return (
        <SortingProvider appendToUrl>
            <PageProvider pageSize={props?.pageSize} appendToUrl>
                <PageLibraryContent />
            </PageProvider>
        </SortingProvider>
    );
};

const PageLibraryContent = () => {
    const navigate = useNavigate();
    const { sortBy } = useSorting();

    const { pages, search, filter } = usePageSummarySearch();
    const { properties } = usePageLibraryProperties();

    const [filters, setFilters] = useState<Filter[]>([]);

    const handleAddnew = () => {
        // this should be a NavLinkButton
        navigate(`/page-builder/add/page`);
    };

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
                        <Button type="button" onClick={handleAddnew}>
                            Create new page
                        </Button>
                    </header>
                    <PageLibraryMenu
                        properties={properties}
                        filters={filters}
                        onSearch={search}
                        onFilter={handleFilter}
                        onDownload={handleDownloadCSV}
                        onPrint={handleDownloadPDF}
                    />
                    <PageLibraryTable summaries={pages} onSort={sortBy} />
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
