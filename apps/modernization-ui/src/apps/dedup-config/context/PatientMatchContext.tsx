import React, { createContext, useContext, useState } from 'react';
import { DataElement } from '../const/init';

export type Method = { value: string; name: string }; // Update Method type to be an array of objects with value and name

export type BlockingCriteria = {
    field: DataElement;
    method: Method;
};

type MatchingCriteria = {
    field: DataElement;
    method: Method;
};

type PatientMatchContextProps = {
    blockingCriteria: BlockingCriteria[];
    setBlockingCriteria: (criteria: BlockingCriteria[]) => void;
    removeBlockingCriteria: (value: string) => void;
    matchingCriteria: MatchingCriteria[];
    setMatchingCriteria: (criteria: MatchingCriteria[]) => void;
    removeMatchingCriteria: (value: string) => void;
    availableMethods: Method[];
};

const PatientMatchContext = createContext<PatientMatchContextProps | undefined>(undefined);

export const availableMethods: Method[] = [
    { value: 'EQUALS', name: 'Equals' },
    { value: 'NOT_EQUAL_TO', name: 'Not equal to' },
    { value: 'STARTS_WITH', name: 'Starts with' },
    { value: 'CONTAINS', name: 'Contains' }
];

const PatientMatchContextProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [blockingCriteria, setBlockingCriteria] = useState<BlockingCriteria[]>([]);
    const [matchingCriteria, setMatchingCriteria] = useState<MatchingCriteria[]>([]);

    const removeBlockingCriteria = (value: string) => {
        setBlockingCriteria((prevCriteria) =>
            prevCriteria.filter((criteria) => criteria.field.name !== value && criteria.method.value !== value)
        );
    };

    // Method to remove matching criteria by field name or method value
    const removeMatchingCriteria = (value: string) => {
        setMatchingCriteria((prevCriteria) =>
            prevCriteria.filter((criteria) => criteria.field.name !== value && criteria.method.value !== value)
        );
    };

    return (
        <PatientMatchContext.Provider
            value={{
                blockingCriteria,
                setBlockingCriteria,
                removeBlockingCriteria,
                matchingCriteria,
                setMatchingCriteria,
                removeMatchingCriteria,
                availableMethods
            }}>
            {children}
        </PatientMatchContext.Provider>
    );
};

export const usePatientMatchContext = () => {
    const context = useContext(PatientMatchContext);
    if (!context) {
        throw new Error('usePatientMatchContext must be used within a PatientMatchContextProvider');
    }
    return context;
};

export default PatientMatchContextProvider;
