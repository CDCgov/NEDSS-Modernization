import React, { createContext, useContext, useState } from 'react';
import { BlockingCriteria, MatchingCriteria, Method } from '../types';

type PatientMatchContextProps = {
    blockingCriteria: BlockingCriteria[];
    setBlockingCriteria: (criteria: BlockingCriteria[]) => void;
    removeBlockingCriteria: (value: string) => void;
    matchingCriteria: MatchingCriteria[];
    setMatchingCriteria: (criteria: MatchingCriteria[]) => void;
    removeMatchingCriteria: (value: string) => void;
    availableMethods: Method[];
    totalLogOdds: number | undefined;
    setTotalLogOdds: (logOdds: number | undefined) => void;
    lowerBound: number | undefined;
    upperBound: number | undefined;
    setLowerBound: (value: number) => void;
    setUpperBound: (value: number) => void;
    saveBounds: (lower: number, upper: number) => void;
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
    const [totalLogOdds, setTotalLogOdds] = useState<number | undefined>(undefined);
    const [lowerBound, setLowerBound] = useState<number | undefined>(undefined);
    const [upperBound, setUpperBound] = useState<number | undefined>(undefined);

    const saveBounds = (lower: number, upper: number) => {
        setLowerBound(lower);
        setUpperBound(upper);
    };

    const removeMatchingCriteria = (value: string) => {
        const updatedCriteria = matchingCriteria.filter((criteria) => criteria.field.name !== value);
        setMatchingCriteria(updatedCriteria);
    };

    const removeBlockingCriteria = (value: string) => {
        setBlockingCriteria((prevCriteria) => prevCriteria.filter((criteria) => criteria.field.name !== value));
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
                availableMethods,
                totalLogOdds,
                setTotalLogOdds,
                lowerBound,
                upperBound,
                setLowerBound,
                setUpperBound,
                saveBounds
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
