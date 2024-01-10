/* eslint-disable camelcase */
import { Condition } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { useContext } from 'react';
import './ConditionLibraryTable.scss';
import { Link } from 'react-router-dom';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';
import { conditionTableColumns } from 'apps/page-builder/constant/conditionLibrary';

interface ConditionLibraryTableProps {
    conditions: Condition[];
    currentPage: number;
    pageSize: number;
    totalElements: number;
}

const asTableRow = (condition: Condition): TableBody => ({
    id: condition.id,
    expanded: false,
    tableDetails: [
        {
            id: 1,
            title: (
                <div className="page-name">
                    <Link to={`/page-builder/edit/condition/${condition.id}`}>{condition.name}</Link>
                </div>
            )
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

const asTableRows = (conditions: Condition[]): TableBody[] => conditions.map(asTableRow);

const ConditionLibraryTable = ({ conditions, currentPage, pageSize, totalElements }: ConditionLibraryTableProps) => {
    const tableRows = asTableRows(conditions);
    const { setCurrentPage, handleSort } = useContext(ConditionsContext);

    return (
        <TableComponent
            contextName="conditions"
            tableHeader="Condition Library"
            tableHead={conditionTableColumns}
            tableBody={tableRows}
            isPagination={true}
            pageSize={pageSize}
            rangeSelector={true}
            totalResults={totalElements}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};

export default ConditionLibraryTable;
