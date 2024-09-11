import React, { createContext, useContext, useEffect, useState } from 'react';

const DataElementsContext = createContext<DataElementsContextProps | undefined>(undefined);

type DataElementsContextProps = {
    dataElements?: DataElement[];
    setDataElements: (dataElements?: DataElement[]) => void;
};

export type DataElement = {
    name: string;
    active: boolean;
    m: number;
    u: number;
    threshold: number;
};

const DataElementsContextProvider: React.FC<{
    children: React.ReactNode;
}> = ({ children }) => {
    const [dataElements, setDataElements] = useState<DataElement[]>();

    useEffect(() => {
        const storadElements = localStorage.getItem('dataElements');
        if (storadElements) {
            setDataElements(JSON.parse(storadElements));
        }
    }, []);

    return (
        <DataElementsContext.Provider value={{ dataElements, setDataElements }}>
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
