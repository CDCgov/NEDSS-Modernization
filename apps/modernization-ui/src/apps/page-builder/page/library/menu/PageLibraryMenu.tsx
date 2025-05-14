import { OverlayPanel } from 'overlay';
import { Filter, FilterPanel, Property } from 'filters';
import { Icon } from 'design-system/icon';
import { LinkButton, Button } from 'design-system/button';
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
                        onClick={() => setOverlayVisible(!overlayVisible)}
                        secondary
                        icon={<Icon name="filter_alt" />}
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
                    icon={<Icon name="print" />}
                    secondary
                />

                <Button
                    data-tooltip-position="top"
                    aria-label="Download as csv"
                    type="button"
                    className={styles.icon}
                    secondary
                    onClick={onDownloadCsv}
                    icon={<Icon name="file_download" />}
                />
            </menu>
        </section>
    );
};

export { PageLibraryMenu };
