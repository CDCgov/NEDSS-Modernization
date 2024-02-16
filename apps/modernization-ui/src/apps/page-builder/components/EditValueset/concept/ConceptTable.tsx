import { Concept } from 'apps/page-builder/generated';
import { ConceptSort, SortField } from 'apps/page-builder/hooks/api/useFindConcepts';
import { TableBody, TableComponent } from 'components/Table';
import { usePage } from 'page';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting/Sort';

export enum Column {
    LOCAL_CODE = 'Local code',
    DISPLAY = 'UI display name',
    CONCEPT_CODE = 'Concept code',
    EFFECTIVE_DATE = 'Effective date'
}

const tableHeaders = [
    { name: Column.LOCAL_CODE, sortable: true },
    { name: Column.DISPLAY, sortable: true },
    { name: Column.CONCEPT_CODE, sortable: true },
    { name: Column.EFFECTIVE_DATE, sortable: true }
];
type Props = {
    concepts: Concept[];
    loading: boolean;
    onSort: (sort: ConceptSort | undefined) => void;
};
export const ConceptTable = ({ concepts, loading, onSort }: Props) => {
    const { page, request } = usePage();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);

    const toRow = (concept: Concept): TableBody => {
        return {
            id: concept.localCode,
            key: concept.localCode,
            tableDetails: [
                {
                    id: 1,
                    title: concept.localCode
                },
                { id: 2, title: concept.display },
                { id: 2, title: concept.conceptCode },
                {
                    id: 3,
                    title: concept.effectiveFromTime
                }
            ]
        };
    };

    useEffect(() => {
        setTableRows(concepts.map(toRow));
    }, [concepts]);

    const handleSort = (name: string, direction: Direction) => {
        if (direction === Direction.None) {
            onSort(undefined);
            return;
        }

        let sortField: SortField | undefined = undefined;
        switch (name) {
            case Column.LOCAL_CODE:
                sortField = SortField.CODE;
                break;
            case Column.DISPLAY:
                sortField = SortField.DISPLAY;
                break;
            case Column.CONCEPT_CODE:
                sortField = SortField.CONCEPT_CODE;
                break;
            case Column.EFFECTIVE_DATE:
                sortField = SortField.EFFECTIVE_DATE;
                break;
        }
        if (sortField) {
            onSort({ field: sortField, direction });
        } else {
            onSort(undefined);
        }
    };

    return (
        <div>
            <TableComponent
                tableHead={tableHeaders}
                tableBody={tableRows}
                display="zebra"
                isPagination={true}
                pageSize={page.pageSize}
                totalResults={page.total}
                currentPage={page.current}
                isLoading={loading}
                handleNext={request}
                sortData={handleSort}
            />
        </div>
    );
};
