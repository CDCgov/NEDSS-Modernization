import React, { createContext, useContext, useState } from 'react';
import { DataElement } from './DataElementsContext';

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
    matchingCriteria: MatchingCriteria[];
    setMatchingCriteria: (criteria: MatchingCriteria[]) => void;
    availableMethods: Method[]; // Add availableMethods to the context
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

    return (
        <PatientMatchContext.Provider
            value={{
                blockingCriteria,
                setBlockingCriteria,
                matchingCriteria,
                setMatchingCriteria,
                availableMethods // Provide available methods in context
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
