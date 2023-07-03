/* eslint-disable camelcase */
import { PageSummary, PageSummaryControllerService, Page_PageSummary_ } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { asLocalDate } from 'date';
import { useContext, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import { UserContext } from 'user';
import './ManagePagesTable.scss';

export enum Header {
    PageName = 'Page name',
    EventType = 'Event name',
    RelatedConditions = 'Related conditions',
    Status = 'Status',
    LastUpdate = 'Last updated',
    LastUpdatedBy = 'Last updated by'
}

type SortCriteria = {
    name?: Header;
    type?: Direction;
};

const tableHead = [
    { name: Header.PageName, sortable: true },
    { name: Header.EventType, sortable: true },
    { name: Header.RelatedConditions, sortable: false },
    { name: Header.Status, sortable: true },
    { name: Header.LastUpdate, sortable: true },
    { name: Header.LastUpdatedBy, sortable: true }
];

const asTableBody = (page: PageSummary): TableBody => ({
    id: page.name,
    checkbox: false,
    tableDetails: [
        {
            id: 1,
            title: <div>{page?.name}</div> || null
        },
        { id: 2, title: <div className="event-text">{page?.eventType?.display}</div> || null },
        {
            id: 3,
            title:
                (
                    <div className="condition-list">
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

const asTableBodies = (pages: PageSummary[] | undefined): TableBody[] => pages?.map(asTableBody) || [];

const buildSort = (sort: SortCriteria): string | undefined => {
    if (sort.name && sort.type && sort.type !== Direction.None) {
        let sortField: string | undefined;
        switch (sort.name) {
            case Header.PageName:
                sortField = 'name';
                break;
            case Header.EventType:
                sortField = 'eventType';
                break;
            case Header.Status:
                sortField = 'status';
                break;
            case Header.LastUpdate:
                sortField = 'lastUpdate';
                break;
            case Header.LastUpdatedBy:
                sortField = 'lastUpdateBy';
                break;
            default:
                sortField = undefined;
        }
        if (sortField) {
            return `${sortField},${sort.type}`;
        }
    } else {
        return undefined;
    }
};

export const ManagePagesTable = () => {
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [criteria, setCriteria] = useState<SortCriteria>({});
    const { state } = useContext(UserContext);
    const [pageSize] = useState(10);
    const [page, setPage] = useState(1);
    const [totalResults, setTotalResults] = useState(0);

    const fetchPageSummaries = () => {
        PageSummaryControllerService.searchUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            request: { search: '' },
            page: page - 1,
            size: pageSize,
            sort: buildSort(criteria)
        }).then((response: Page_PageSummary_) => {
            setBodies(asTableBodies(response.content));
            setTotalResults(response.totalElements || 0);
        });
    };

    useEffect(() => {
        fetchPageSummaries();
    }, [page, pageSize, criteria]);

    useEffect(() => {}, [page, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Header, type: direction as Direction });
    };

    return (
        <TableComponent
            tableHeader="Page Library"
            tableHead={tableHead}
            tableBody={bodies}
            isPagination={true}
            pageSize={pageSize}
            totalResults={totalResults}
            currentPage={page}
            handleNext={setPage}
            sortData={handleSort}
        />
    );
};
