import { PageSummary } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { internalizeDate } from 'date';
import { usePage } from 'page';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Direction } from 'sorting';

import styles from './page-library-table.module.scss';
import { ConditionsDisplay } from './ConditionsDisplay';

export enum Column {
    PageName = 'Page name',
    EventType = 'Event type',
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

const asTableRow = (page: PageSummary, enableEdit: boolean): TableBody => ({
    id: page.id,
    tableDetails: [
        {
            id: 1,
            title:
                enableEdit && page.eventType?.value === 'INV' ? (
                    <Link to={`/page-builder/pages/${page.id}`}>{page?.name}</Link>
                ) : (
                    <a href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`}>{page?.name}</a>
                )
        },
        { id: 2, title: page.eventType?.name },
        {
            id: 3,
            title: <ConditionsDisplay conditions={page.conditions ?? []} />
        },
        { id: 4, title: page?.status },
        {
            id: 5,
            title: internalizeDate(page?.lastUpdate)
        },
        { id: 6, title: page?.lastUpdateBy }
    ]
});

const asTableRows = (pages: PageSummary[], enableEdit: boolean): TableBody[] =>
    pages.map((p) => asTableRow(p, enableEdit));

type Props = {
    summaries: PageSummary[];
    searching?: boolean;
    onSort: (name: string, direction: Direction) => void;
    enableEdit: boolean;
};

export const PageLibraryTable = ({ enableEdit, summaries, searching = false, onSort }: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const {
        page: { pageSize, total, current },
        request
    } = usePage();

    useEffect(() => {
        setTableRows(asTableRows(summaries, enableEdit));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        const property = resolveSortProperty(name);
        onSort(property, direction);
    };

    return (
        <TableComponent
            display="zebra"
            isLoading={searching}
            className={`${styles.pageTable} pageLibraryTable`}
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
