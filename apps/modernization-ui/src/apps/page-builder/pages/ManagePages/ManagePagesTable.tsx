/* eslint-disable camelcase */
import { PageSummary } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { asLocalDate } from 'date';
import { usePage } from 'page';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting';
import './ManagePagesTable.scss';

export enum Column {
    PageName = 'Page name',
    EventType = 'Event name',
    RelatedConditions = 'Related conditions',
    Status = 'Status',
    LastUpdate = 'Last updated',
    LastUpdatedBy = 'Last updated by'
}

const tableColumns = [
    { name: Column.PageName, sortable: true },
    { name: Column.EventType, sortable: true },
    { name: Column.RelatedConditions, sortable: false },
    { name: Column.Status, sortable: true },
    { name: Column.LastUpdate, sortable: true },
    { name: Column.LastUpdatedBy, sortable: true }
];

type Props = {
    sortChange: (sort?: string) => void;
    summaries: PageSummary[];
};
export const ManagePagesTable = ({ summaries, sortChange }: Props) => {
    const { page, request } = usePage();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);

    const asTableRow = (page: PageSummary): TableBody => ({
        id: page.name,
        checkbox: false,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.name}</div> || null
            },
            { id: 2, title: <div className="event-text">{page?.eventType?.display}</div> || null },
            {
                id: 3,
                title:
                    (
                        <div>
                            {page?.conditions?.map((c, index) => (
                                <div key={index}>{c.name}</div>
                            ))}{' '}
                        </div>
                    ) || null
            },
            { id: 4, title: page?.status || null },
            { id: 5, title: page?.lastUpdate ? asLocalDate(page.lastUpdate).toLocaleString() : null },
            { id: 6, title: page?.lastUpdateBy || null }
        ]
    });

    const asTableRows = (pages: PageSummary[] | undefined): TableBody[] => pages?.map(asTableRow) || [];

    /*
     * Converts header and Direction to API compatible sort string such as "name,asc"
     */
    const toSortString = (name: string, direction: Direction): string | undefined => {
        if (name && direction && direction !== Direction.None) {
            switch (name) {
                case Column.PageName:
                    return `name,${direction}`;
                case Column.EventType:
                    return `eventType,${direction}`;
                case Column.Status:
                    return `status,${direction}`;
                case Column.LastUpdate:
                    return `lastUpdate,${direction}`;
                case Column.LastUpdatedBy:
                    return `lastUpdateBy,${direction}`;
                default:
                    return undefined;
            }
        }
        return undefined;
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        sortChange(toSortString(name, direction));
    };

    return (
        <TableComponent
            tableHeader="Page Library"
            tableHead={tableColumns}
            tableBody={tableRows}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={page.total}
            currentPage={page.current}
            handleNext={request}
            sortData={handleSort}
        />
    );
};
