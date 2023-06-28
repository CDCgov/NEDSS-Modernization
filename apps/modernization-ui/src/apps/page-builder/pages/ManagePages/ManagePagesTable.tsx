import { TableComponent, TableBody } from 'components/Table/Table';
import { useState, useEffect } from 'react';
import { Pages, Headers } from './Pages';
import { SortCriteria } from './ManagePagesSorter';
import { Direction } from 'sorting';
import { sort } from './ManagePagesSorter';
import { usePage } from 'page';

type Props = {
    pages?: Pages[];
    pageSize?: number;
};

const tableHead = [
    { name: Headers.PageName, sortable: true },
    { name: Headers.EventType, sortable: true },
    { name: Headers.RelatedConditions, sortable: true },
    { name: Headers.Status, sortable: true },
    { name: Headers.LastUpdatedBy, sortable: true }
];

const asTableBody = (page: Pages): TableBody => ({
    id: page.pageName,
    checkbox: false,
    tableDetails: [
        { id: 1, title: page?.pageName || null },
        { id: 2, title: page?.eventType || null },
        { id: 3, title: page?.relatedConditions || null },
        { id: 4, title: page?.status || null },
        { id: 5, title: page?.lastUpdatedBy || null }
    ]
});

const asTableBodies = (pages: Pages[]): TableBody[] => pages?.map(asTableBody) || [];
export const ManagePagesTable = ({ pages }: Props) => {
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [criteria, setCriteria] = useState<SortCriteria>({});
    const { page, request } = usePage();

    useEffect(() => {
        if (pages && !criteria) {
            setBodies(asTableBodies(pages));
        } else if (pages && criteria) {
            const sorted = sort(pages, criteria);
            setBodies(asTableBodies(sorted));
        }
    }, [pages, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Headers, type: direction as Direction });
    };

    return (
        <TableComponent
            tableHeader="Page Library"
            tableHead={tableHead}
            tableBody={bodies}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={pages?.length}
            currentPage={page.current}
            handleNext={request}
            sortData={handleSort}
        />
    );
};
