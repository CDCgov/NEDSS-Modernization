import React, { useContext, useState } from 'react';
import { DragStart, DraggableLocation, DropResult, DragUpdate } from 'react-beautiful-dnd';

// type ReorderProps = (source: DraggableLocation, destination: DraggableLocation) => void;

type ReorderContextProps = {
    handleDragEnd: (result: DropResult) => void;
    handleDragStart: (result: DragStart) => void;
    handleDragUpdate: (result: DragUpdate) => void;
    dragTarget: { droppableId: string; index: number } | undefined;
};

const ReorderContext = React.createContext<ReorderContextProps | undefined>(undefined);

const ReorderContextProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [dragTarget, setDragTarget] = useState<DraggableLocation | undefined>();

    const handleConfigMove = (source: DraggableLocation, destination: DraggableLocation) => {
        const { index: destinationIndex } = destination;
        const { index: sourceIndex } = source;
        console.log('HANDLE CONFIG MOVE', sourceIndex, destinationIndex);
        if (destination.droppableId === 'all-configurations') {
            console.log('move');
            // const { dataElements } = JSON.parse(localStorage.getItem('passConfigurations') as string);
            // const newDataElements = [...dataElements];

            // // Move the item from sourceIndex to destIndex
            // const [movedItem] = newDataElements.splice(sourceIndex, 1);
            // newDataElements.splice(destIndex, 0, movedItem);

            // // Save the new arrangement to localStorage
            // localStorage.setItem('passConfigurations', JSON.stringify({ dataElements: newDataElements }));
        }
    };

    const handleDragEnd = (result: DropResult) => {
        const { source, destination } = result;
        console.log('handleDragEnd', result);
        // If there's no destination, we can just return
        if (!destination) return;
        // Log the result for debugging
        console.log('Source:', source);
        console.log('Destination:', destination);

        // Proceed with moving the configuration
        handleConfigMove(source, destination);
    };

    const handleDragUpdate = (event: DragUpdate) => {
        const { destination } = event;
        if (!destination) return;
        setDragTarget(destination);
    };

    const handleDragStart = (event: DragStart) => {
        console.log('drag start', event);
        setDragTarget(event.source);
    };

    return (
        <ReorderContext.Provider
            value={{
                handleDragEnd,
                handleDragStart,
                handleDragUpdate,
                dragTarget
            }}>
            {children}
        </ReorderContext.Provider>
    );
};

export function useReorderContext() {
    const context = useContext(ReorderContext);
    if (!context) {
        throw new Error('useReorderContext must be used within a ReorderContextProvider');
    }
    return context;
}

export default ReorderContextProvider;
