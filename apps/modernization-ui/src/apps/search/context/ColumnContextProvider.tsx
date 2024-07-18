import { Column } from 'design-system/table';
import { PatientSearchResult, InvestigationResults, LabReportResults } from 'generated/graphql/schema';
import React, { useContext, useState } from 'react';
import { DraggableLocation, DropResult } from 'react-beautiful-dnd';

type ColumnProps = (source: DraggableLocation, destination: DraggableLocation) => void;

type ColumnContextProps = {
    register: (columns: Column<PatientSearchResult | InvestigationResults | LabReportResults>[]) => void;
    handleDragEnd: (result: DropResult) => void;
    displayColumns: DisplayColumn[];
    saveColumns: () => void;
    resetColumns: () => void;
};

export type DisplayColumn = {
    id: string;
    name: string;
    sortable: boolean;
    visible: boolean;
};

const ColumnContext = React.createContext<ColumnContextProps | undefined>(undefined);

const ColumnProvider: React.FC<{
    children: React.ReactNode;
}> = ({ children }) => {
    const [displayColumns, setDisplayColumns] = useState<DisplayColumn[]>([]);

    const register = (columns: Column<PatientSearchResult | InvestigationResults | LabReportResults>[]) => {
        setDisplayColumns(
            columns.map((column) => ({
                id: column.id,
                name: column.name,
                sortable: column.sortable || true,
                visible: true
            }))
        );
    };

    let tempColumns = displayColumns;

    const moveColumn: ColumnProps = (source, destination) => {
        tempColumns.splice(destination.index, 0, tempColumns.splice(source.index, 1)[0]);
    };

    const resetColumns = () => {
        tempColumns = displayColumns;
    };

    const saveColumns = () => {
        const newArr = [...tempColumns];
        return setDisplayColumns(newArr);
    };

    const handleDragEnd = (result: DropResult) => {
        if (!result.destination) return;
        const { source, destination } = result;
        moveColumn(source, destination);
    };

    return (
        <ColumnContext.Provider value={{ register, displayColumns, handleDragEnd, saveColumns, resetColumns }}>
            {children}
        </ColumnContext.Provider>
    );
};

export function useColumnContext() {
    const context = useContext(ColumnContext);
    if (context === undefined) {
        throw new Error('useColumnContext must be used inside ColumnProvider');
    }

    return context;
}

export default ColumnProvider;
