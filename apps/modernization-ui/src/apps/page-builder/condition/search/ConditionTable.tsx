import { ConditionSort, ConditionSortField } from 'apps/page-builder/condition/search/useConditionSearch';
import { Condition } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table';
import { usePagination } from 'pagination';
import { ChangeEvent, useEffect, useState } from 'react';
import { Direction } from 'sorting';

enum ConditionColumn {
    Condition = 'Condition',
    Code = 'Code',
    ProgramArea = 'Program area',
    ConditionFamily = 'Condition family',
    CoinfectionGroup = 'Coinfection group',
    NND = 'NND',
    InvestigationPage = 'Investigation page',
    Status = 'Status'
}

const conditionTableColumns = [
    { name: ConditionColumn.Condition, sortable: true },
    { name: ConditionColumn.Code, sortable: true },
    { name: ConditionColumn.ProgramArea, sortable: true },
    { name: ConditionColumn.ConditionFamily, sortable: true },
    { name: ConditionColumn.CoinfectionGroup, sortable: true },
    { name: ConditionColumn.NND, sortable: true },
    { name: ConditionColumn.InvestigationPage, sortable: true },
    { name: ConditionColumn.Status, sortable: true }
];

const columnToSortMap = new Map<string, ConditionSortField>([
    [ConditionColumn.Condition, ConditionSortField.CONDITION],
    [ConditionColumn.Code, ConditionSortField.CODE],
    [ConditionColumn.ProgramArea, ConditionSortField.PROGRAM_AREA],
    [ConditionColumn.ConditionFamily, ConditionSortField.CONDITION_FAMILY],
    [ConditionColumn.CoinfectionGroup, ConditionSortField.COINFECTION_GROUP],
    [ConditionColumn.NND, ConditionSortField.NND],
    [ConditionColumn.InvestigationPage, ConditionSortField.INVESTIGATION_PAGE],
    [ConditionColumn.Status, ConditionSortField.STATUS]
]);

const asTableRow = (condition: Condition): TableBody => ({
    id: condition.id,
    expanded: false,
    selectable: true,
    tableDetails: [
        {
            id: 1,
            title: <div className="condition-name">{condition.name}</div>
        },
        { id: 2, title: condition.id },
        { id: 3, title: condition.programArea },
        {
            id: 4,
            title: condition.conditionFamily
        },
        { id: 5, title: condition.coinfectionGroup },

        { id: 6, title: condition.nndInd },
        { id: 7, title: condition.page },
        { id: 8, title: condition.status === 'A' ? 'Active' : 'Inactive' }
    ]
});

type Props = {
    conditions: Condition[];
    isLoading: boolean;
    onSelectionChange: (selection: number[]) => void;
    onSort?: (sort?: ConditionSort) => void;
};
export const ConditionTable = ({ conditions, isLoading, onSelectionChange, onSort }: Props) => {
    const { page, request } = usePagination();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selected, setSelected] = useState<number[]>([]);

    useEffect(() => {
        setSelected([]);
        setTableRows(conditions.map(asTableRow));
    }, [JSON.stringify(conditions)]);

    useEffect(() => {
        onSelectionChange(selected);
    }, [selected]);

    const handleSelect = (event: ChangeEvent<HTMLInputElement>, item: { id: number }) => {
        if (event.target.checked) {
            setSelected((current) => [...current, item.id]);
        } else {
            setSelected((current) => current.filter((id) => id !== item.id));
        }
    };

    const handleSort = (name: string, direction: Direction) => {
        if (direction === Direction.None) {
            // sort cleared
            onSort?.(undefined);
        } else {
            // get sortable field from column map
            const sortableField = columnToSortMap.get(name);
            const sort: ConditionSort = { field: sortableField ?? ConditionSortField.CONDITION, direction };
            onSort?.(sort);
        }
    };

    return (
        <TableComponent
            display="zebra"
            isLoading={isLoading}
            sortData={handleSort}
            tableHead={conditionTableColumns}
            tableBody={tableRows}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={page.total}
            currentPage={page.current}
            handleNext={request}
            selectable={true}
            rangeSelector={true}
            handleSelected={handleSelect}
        />
    );
};
