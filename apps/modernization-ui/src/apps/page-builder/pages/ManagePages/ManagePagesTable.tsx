/* eslint-disable camelcase */
import { PageSummary } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { asLocalDate } from 'date';
import { useContext, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import './ManagePagesTable.scss';
import { TableMenu } from 'apps/page-builder/components/TableMenu/TableMenu';
import { PagesContext } from 'apps/page-builder/context/PagesContext';

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
    // sortChange: (sort?: string) => void;
    summaries: PageSummary[];
    currentPage: number;
    pageSize: number;
    totalElements: number;
};
export const ManagePagesTable = ({ summaries, currentPage, pageSize, totalElements }: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const { setCurrentPage, setSortBy, setSortDirection } = useContext(PagesContext);

    const asTableRow = (page: PageSummary): TableBody => ({
        id: page.name,
        checkbox: false,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.name}</div> || null
            },
            { id: 2, title: <div className="event-text">{page?.eventType?.name}</div> || null },
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
    const toSortString = (name: string): string | undefined => {
        if (name) {
            switch (name) {
                case Column.PageName:
                    setSortBy('name');
                    break;
                case Column.EventType:
                    setSortBy('eventType');
                    break;
                case Column.Status:
                    setSortBy('status');
                    break;
                case Column.LastUpdate:
                    setSortBy('lastUpdate');
                    break;
                case Column.LastUpdatedBy:
                    setSortBy('lastUpdateBy');
                    break;
                default:
                    return '';
            }
        }
        return '';
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        if (currentPage > 1 && setCurrentPage) {
            setCurrentPage(1);
        }
        toSortString(name);
        setSortDirection(direction);
    };

    return (
        <TableComponent
            tableHeader="Page Library"
            tableHead={tableColumns}
            tableBody={tableRows}
            isPagination={true}
            pageSize={pageSize}
            totalResults={totalElements}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
            buttons={<TableMenu tableType="page" />}
            rangeSelector={true}
        />
    );
};
