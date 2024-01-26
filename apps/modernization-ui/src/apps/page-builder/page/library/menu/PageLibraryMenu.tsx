import { Button, Icon } from '@trussworks/react-uswds';
import { OverlayPanel } from 'overlay';
import { Filter, FilterPanel, Property } from 'filters';
import { LinkButton } from 'components/button';
import { Search } from 'components/Search';

import styles from './page-library-menu.module.scss';
import { FilterDisplay } from './FilterDisplay';
import { useState } from 'react';

type Props = {
    properties: Property[];
    filters: Filter[];
    onSearch: (keyword?: string) => void;
    onFilter: (filters: Filter[]) => void;
    onDownload: () => void;
    onPrint: () => void;
};
const PageLibraryMenu = ({ properties, filters, onSearch, onFilter, onDownload, onPrint }: Props) => {
    const [overlayVisible, setOverlayVisible] = useState<boolean>(false);
    return (
        <section className={styles.menu}>
            <FilterDisplay onClickFilter={() => setOverlayVisible(!overlayVisible)} filters={filters} />
            <OverlayPanel
                position="right"
                overlayVisible={overlayVisible}
                toggle={() => (
                    <Button
                        type="button"
                        onClick={() => setOverlayVisible(!overlayVisible)}
                        outline
                        className={styles.filterButton}>
                        <Icon.FilterAlt />
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
                placeholder="Search by page or condition name"
                onSearch={onSearch}
            />
            <menu>
                <LinkButton target="_self" href="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true">
                    Page porting
                </LinkButton>
                <Button
                    aria-label="download search results as csv"
                    type="button"
                    onClick={onPrint}
                    className={styles.icon}
                    outline>
                    <Icon.Print size={3} data-testid="print-icon" />
                </Button>
                <Button
                    aria-label="download search results as pdf"
                    type="button"
                    className={styles.icon}
                    outline
                    onClick={onDownload}
                    data-testid="file-download">
                    <Icon.FileDownload size={3} />
                </Button>
            </menu>
        </section>
    );
};

export { PageLibraryMenu };
