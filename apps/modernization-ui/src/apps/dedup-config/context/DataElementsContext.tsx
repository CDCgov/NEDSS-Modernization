import React, { createContext, useContext, useEffect, useState } from 'react';
import { DataElement } from '../types';

const DataElementsContext = createContext<DataElementsContextProps | undefined>(undefined);

type DataElementsContextProps = {
    dataElements?: DataElement[];
    setDataElements: (dataElements: DataElement[]) => void;
    belongingnessRatio: number | undefined;
    setBelongingnessRatio: (belongingnessRatio: number | undefined) => void;
};

const DataElementsContextProvider: React.FC<{
    children: React.ReactNode;
}> = ({ children }) => {
    const [dataElements, setDataElements] = useState<DataElement[] | undefined>();
    const [belongingnessRatio, setBelongingnessRatio] = useState<number | undefined>(undefined);

    useEffect(() => {
        const storedElements = localStorage.getItem('dataElements');
        if (storedElements) {
            console.log('dataElements', JSON.parse(storedElements));
            setDataElements(JSON.parse(storedElements));
        }
    }, []);

    return (
        <DataElementsContext.Provider
            value={{ dataElements, setDataElements, belongingnessRatio, setBelongingnessRatio }}>
            {children}
        </DataElementsContext.Provider>
    );
};

export const useDataElementsContext = () => {
    const context = useContext(DataElementsContext);

    if (context === undefined) {
        throw new Error('useDataElementsContext must be used inside DataElementsContextProvider');
    }

    return context;
};

export default DataElementsContextProvider;
