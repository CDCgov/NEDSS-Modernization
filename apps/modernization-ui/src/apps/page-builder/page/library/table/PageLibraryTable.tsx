import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { internalizeDate } from 'date';
import { Direction } from 'sorting';
import { TableBody, TableComponent } from 'components/Table/Table';
import { PageSummary } from 'apps/page-builder/generated';

import { usePage } from 'page';

import styles from './page-library-table.module.scss';

export enum Column {
    PageName = 'Page name',
    EventType = 'Event name',
    RelatedConditions = 'Related condition(s)',
    Status = 'Status',
    LastUpdate = 'Last updated',
    LastUpdatedBy = 'Last updated by'
}

const headers = [
    { name: Column.PageName, sortable: true },
    { name: Column.EventType, sortable: true },
    { name: Column.RelatedConditions, sortable: false },
    { name: Column.Status, sortable: true },
    { name: Column.LastUpdate, sortable: true },
    { name: Column.LastUpdatedBy, sortable: true }
];

const asTableRow = (page: PageSummary): TableBody => ({
    id: page.id,
    tableDetails: [
        {
            id: 1,
            title: <Link to={`/page-builder/edit/page/${page.id}`}>{page?.name}</Link>
        },
        { id: 2, title: page.eventType?.name },
        {
            id: 3,
            title: page.conditions?.map((condition) => condition.name).join('\n')
        },
        { id: 4, title: page?.status },
        {
            id: 5,
            title: internalizeDate(page?.lastUpdate)
        },
        { id: 6, title: page?.lastUpdateBy }
    ]
});

const asTableRows = (pages: PageSummary[]): TableBody[] => pages.map(asTableRow);

type Props = {
    summaries: PageSummary[];
    searching?: boolean;
    onSort: (name: string, direction: Direction) => void;
};

export const PageLibraryTable = ({ summaries, searching = false, onSort }: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);

    const {
        page: { pageSize, total, current },
        request
    } = usePage();

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        const property = resolveSortProperty(name);
        onSort(property, direction);
    };

    return (
        <TableComponent
            display="zebra"
            isLoading={searching}
            className={styles.pages}
            tableHead={headers}
            tableBody={tableRows}
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={current}
            handleNext={request}
            sortData={handleSort}
            rangeSelector={true}
        />
    );
};

/*
 * Converts header and Direction to API compatible sort string such as "name,asc"
 */
const resolveSortProperty = (name: string): string => {
    switch (name) {
        case Column.PageName:
            return 'name';
        case Column.EventType:
            return 'eventType';

        case Column.Status:
            return 'status';

        case Column.RelatedConditions:
            return 'conditions';

        case Column.LastUpdate:
            return 'lastUpdate';

        case Column.LastUpdatedBy:
            return 'lastUpdatedBy';

        default:
            return 'id';
    }
};
