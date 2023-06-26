import { TableComponent, TableBody } from 'components/Table/Table';
import { useState, useEffect } from 'react';
import { Pages } from './Pages';
import { SortCriteria } from 'pages/patient/profile/document/PatientDocumentSorter';

type Props = {
    pages?: Pages[];
    pageSize?: number;
};

const tableHead = [
    { name: 'Page name', sortable: true, sort: 'all' },
    { name: 'Event type', sortable: true, sort: 'all' },
    { name: 'Related conditions', sortable: true, sort: 'all' },
    { name: 'Status', sortable: true, sort: 'all' },
    { name: 'Last Updated by', sortable: true, sort: 'all' }
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
    const [criteria] = useState<SortCriteria>({});

    useEffect(() => {
        if (pages) {
            // const sorted = sort(pages, criteria);
            setBodies(asTableBodies(pages));
        }
    }, [pages, criteria]);

    // const handleSort = (name: string, direction: string): void => {
    //     setCriteria({ name: name as Headers, type: direction as Direction });
    // };

    return (
        <TableComponent
            tableHeader="Page Library"
            tableHead={tableHead}
            tableBody={bodies}
            // isPagination={true}
            // pageSize={page.pageSize}
            totalResults={pages?.length}
            // currentPage={page.current}
            // handleNext={request}
            // sortData={handleSort}
        />
    );
};
