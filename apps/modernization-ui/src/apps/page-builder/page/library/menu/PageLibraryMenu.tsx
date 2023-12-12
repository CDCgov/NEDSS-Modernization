import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { OverlayPanel } from 'overlay';
import { Filter, FilterPanel, Property } from 'filters';
import { LinkButton } from 'components/button';
import { Search } from 'components/Search';

import styles from './page-library-menu.module.scss';

type Props = {
    properties: Property[];
    filters: Filter[];
    onSearch: (keyword?: string) => void;
    onFilter: (filters: Filter[]) => void;
    onDownload: () => void;
    onPrint: () => void;
};
const PageLibraryMenu = ({ properties, filters, onSearch, onFilter, onDownload, onPrint }: Props) => {
    return (
        <section className={styles.menu}>
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
                <OverlayPanel
                    position="right"
                    toggle={({ toggle }) => (
                        <Button type="button" onClick={toggle} outline>
                            <Icon.FilterAlt />
                            Filter
                        </Button>
                    )}
                    render={(close) => (
                        <FilterPanel
                            label="Pages"
                            properties={properties}
                            filters={filters}
                            close={close}
                            onApply={onFilter}
                        />
                    )}
                />
                <Button type="button" className={styles.icon} outline>
                    <Icon.Print size={3} onClick={onPrint} data-testid="print-icon" />
                </Button>
                <Button type="button" className={styles.icon} outline onClick={onDownload} data-testid="file-download">
                    <Icon.FileDownload size={3} />
                </Button>
            </menu>
        </section>
    );
};

export { PageLibraryMenu };
