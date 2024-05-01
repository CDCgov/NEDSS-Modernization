import { Icon } from '@trussworks/react-uswds';
import { OverlayPanel } from 'overlay';
import { Filter, FilterPanel, Property } from 'filters';
import { LinkButton, Button } from 'components/button';
import { Search } from 'components/Search';

import styles from './page-library-menu.module.scss';
import { FilterDisplay } from './FilterDisplay';
import { useState } from 'react';

type Props = {
    properties: Property[];
    filters: Filter[];
    onSearch: (keyword?: string) => void;
    onFilter: (filters: Filter[]) => void;
    onDownloadCsv: () => void;
    onDownloadPdf: () => void;
};

const PageLibraryMenu = ({ properties, filters, onSearch, onFilter, onDownloadCsv, onDownloadPdf }: Props) => {
    const [overlayVisible, setOverlayVisible] = useState<boolean>(false);
    return (
        <section className={styles.menu}>
            <FilterDisplay onClickFilter={() => setOverlayVisible(!overlayVisible)} filters={filters} />
            <OverlayPanel
                position="right"
                overlayVisible={overlayVisible}
                toggle={() => (
                    <Button
                        id="filter-button"
                        type="button"
                        onClick={() => setOverlayVisible(!overlayVisible)}
                        outline
                        icon={<Icon.FilterAlt />}
                        labelPosition="left"
                        className={styles.filterButton}>
                        Filter
                    </Button>
                )}
                render={() => (
                    <FilterPanel
                        label="Pages"
                        properties={properties}
                        filters={filters}
                        close={() => setOverlayVisible(false)}
                        onApply={onFilter}
                    />
                )}
            />
            <Search
                className={styles.search}
                id="page-search"
                name="search"
                ariaLabel="search page name and  by keyword"
                placeholder="Search by page name, condition name or condition code"
                onSearch={onSearch}
            />
            <menu>
                <LinkButton
                    target="_self"
                    href="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true"
                    data-tooltip-position="top"
                    aria-label="Page porting">
                    Page porting
                </LinkButton>
                <Button
                    data-tooltip-position="top"
                    aria-label="Print this page"
                    type="button"
                    onClick={onDownloadPdf}
                    className={styles.icon}
                    icon={<Icon.Print data-testid="print-icon" />}
                    outline
                />

                <Button
                    data-tooltip-position="top"
                    aria-label="Download as csv"
                    type="button"
                    className={styles.icon}
                    outline
                    onClick={onDownloadCsv}
                    data-testid="file-download"
                    icon={<Icon.FileDownload />}
                />
            </menu>
        </section>
    );
};

export { PageLibraryMenu };
