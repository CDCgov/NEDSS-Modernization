<<<<<<< HEAD
import React, { createContext, useContext, useEffect, useState } from 'react';
=======
import React, { createContext, useContext, useState } from 'react';
>>>>>>> case-dedup-ui

const DataElementsContext = createContext<DataElementsContextProps | undefined>(undefined);

type DataElementsContextProps = {
<<<<<<< HEAD
    dataElements?: DataElement[];
    setDataElements: (dataElements?: DataElement[]) => void;
=======
    dataElements: DataElement[];
    setDataElements: (dataElements: DataElement[]) => void;
    belongingnessRatio: number | undefined;
    setBelongingnessRatio: (belongingnessRatio: number | undefined) => void;
>>>>>>> case-dedup-ui
};

export type DataElement = {
    name: string;
    active: boolean;
    m: number;
    u: number;
    threshold: number;
};

<<<<<<< HEAD
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
=======
export const DataElements: DataElement[] = [
    { name: 'lastName', active: false, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'secondLastName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'firstName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'middleName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'secondMiddleName', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'suffix', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    { name: 'currentSex', active: true, m: 0.5, u: 0.5, threshold: 0.5 },
    {
        name: 'dateOfBirth',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5
    },
    {
        name: 'ssn',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5
    },
    {
        name: 'idType',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'idAssigningAuthority',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'idValue',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'streetAddress1',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'city',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'state',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'zip',
        active: true,
        m: 0.5,
        u: 0.25,
        threshold: 0.5
    },
    {
        name: 'telephone',
        active: true,
        m: 0.5,
        u: 0.1,
        threshold: 0.5
    }
];

const DataElementsContextProvider: React.FC<{
    children: React.ReactNode;
}> = ({ children }) => {
    const [dataElements, setDataElements] = useState(DataElements);
    const [belongingnessRatio, setBelongingnessRatio] = useState<number | undefined>(undefined);

    return (
        <DataElementsContext.Provider
            value={{ dataElements, setDataElements, belongingnessRatio, setBelongingnessRatio }}>
>>>>>>> case-dedup-ui
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
