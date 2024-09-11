<<<<<<< HEAD
import React from 'react';
import DataElementsContextProvider from './DataElementsContext';
import DedupeContextProvider from './DedupeContext';

const PatientMatchContextProvider = ({ children }: { children: React.ReactNode }) => {
    return (
        // Add other ContextProviders here
        <DedupeContextProvider>
            <DataElementsContextProvider>{children}</DataElementsContextProvider>
        </DedupeContextProvider>
    );
};

=======
import React, { createContext, useContext, useState } from 'react';
import { DataElement } from './DataElementsContext';

type BlockingCriteria = {
    field: DataElement;
    method: string;
};

type MatchingCriteria = {
    field: DataElement;
    method: string;
};

type PatientMatchContextProps = {
    blockingCriteria: BlockingCriteria[];
    setBlockingCriteria: (criteria: BlockingCriteria[]) => void;
    matchingCriteria: MatchingCriteria[];
    setMatchingCriteria: (criteria: MatchingCriteria[]) => void;
};

const PatientMatchContext = createContext<PatientMatchContextProps | undefined>(undefined);

const PatientMatchContextProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [blockingCriteria, setBlockingCriteria] = useState<BlockingCriteria[]>([]);
    const [matchingCriteria, setMatchingCriteria] = useState<MatchingCriteria[]>([]);

    return (
        <PatientMatchContext.Provider
            value={{
                blockingCriteria,
                setBlockingCriteria,
                matchingCriteria,
                setMatchingCriteria
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

>>>>>>> case-dedup-ui
export default PatientMatchContextProvider;
