import { Button, Icon } from '@trussworks/react-uswds';
import { Concept } from 'apps/page-builder/generated';
import { ConceptSort, SortField } from 'apps/page-builder/hooks/api/useFindConcepts';
import { TableBody, TableComponent } from 'components/Table';
import { internalizeDate } from 'date';
import { usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { Direction } from 'libs/sorting';
import styles from './concept-table.module.scss';

export enum Column {
    LOCAL_CODE = 'Local code',
    DISPLAY = 'UI display name',
    CONCEPT_CODE = 'Concept code',
    EFFECTIVE_DATE = 'Effective date',
    EDIT_ICON = ''
}

const tableHeaders = [
    { name: Column.LOCAL_CODE, sortable: true },
    { name: Column.DISPLAY, sortable: true },
    { name: Column.CONCEPT_CODE, sortable: true },
    { name: Column.EFFECTIVE_DATE, sortable: true },
    { name: Column.EDIT_ICON, sortable: false }
];
type Props = {
    concepts: Concept[];
    loading: boolean;
    onSort: (sort: ConceptSort | undefined) => void;
    onEditConcept: (concept: Concept) => void;
};
export const ConceptTable = ({ concepts, loading, onSort, onEditConcept }: Props) => {
    const { page, request } = usePagination();
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
                    title: internalizeDate(concept.effectiveFromTime)
                },
                {
                    id: 4,
                    title: (
                        <Button
                            type="button"
                            outline
                            className={styles.editConceptButton}
                            aria-label={`edit concept: ${concept.localCode}`}
                            onClick={() => onEditConcept(concept)}>
                            <Icon.Edit size={3} />
                        </Button>
                    )
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
                className={styles.conceptTable}
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
