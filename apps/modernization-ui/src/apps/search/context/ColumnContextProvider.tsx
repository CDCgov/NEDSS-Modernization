import React, { useContext, useState } from 'react';
import { DraggableLocation, DropResult } from 'react-beautiful-dnd';

type ColumnProps = (source: DraggableLocation, destination: DraggableLocation) => void;

type ColumnContextProps = {
    handleDragEnd: (result: DropResult) => void;
    displayColumns: DisplayColumn[];
    tempColumns: DisplayColumn[];
    saveColumns: () => void;
    resetColumns: () => void;
    toggleHide: (id: string) => void;
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
    successCallback: () => void;
}> = ({ children, successCallback }) => {
    const defaultColumns = [
        { id: 'lastNm', name: 'Legal name', sortable: false, visible: true },
        { id: 'birthTime', name: 'Date of birth', sortable: false, visible: true },
        { id: 'sex', name: 'Sex', sortable: false, visible: true },
        { id: 'id', name: 'Patient ID', sortable: false, visible: true },
        { id: 'address', name: 'Address', sortable: true, visible: true },
        { id: 'phoneNumber', name: 'Phone', sortable: true, visible: true },
        { id: 'names', name: 'Other names', sortable: true, visible: true },
        { id: 'identification', name: 'ID', sortable: true, visible: true },
        { id: 'email', name: 'Email', sortable: true, visible: true }
    ];
    const [displayColumns, setDisplayColumns] = useState(defaultColumns);
    const [tempColumns, setTempColumns] = useState(displayColumns);

    const moveColumn: ColumnProps = (source, destination) => {
        tempColumns.splice(destination.index, 0, tempColumns.splice(source.index, 1)[0]);
    };

    const toggleHide = (id: string) => {
        const updatedColumns = tempColumns.map((column) => {
            if (column.id === id) {
                return { ...column, visible: !column.visible };
            }
            return column;
        });
        setTempColumns(updatedColumns);
    };

    const resetColumns = () => {
        setTempColumns(displayColumns);
    };

    const saveColumns = () => {
        setDisplayColumns(tempColumns);
        successCallback();
    };

    const handleDragEnd = (result: DropResult) => {
        if (!result.destination) return;
        const { source, destination } = result;
        moveColumn(source, destination);
    };

    return (
        <ColumnContext.Provider
            value={{ displayColumns, tempColumns, handleDragEnd, toggleHide, saveColumns, resetColumns }}>
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
