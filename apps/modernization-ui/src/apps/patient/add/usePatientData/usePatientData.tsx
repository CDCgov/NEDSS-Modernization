import { createContext, useContext, useState, ReactNode } from 'react';
import { NewPatientEntry } from '../NewPatientEntry';

type PatientDataContextType = {
    addPatientFormData: NewPatientEntry | null;
    setAddPatientFormData: (data: NewPatientEntry) => void;
};

const PatientDataContext = createContext<PatientDataContextType | undefined>(undefined);

type PatientDataProviderProps = {
    children: ReactNode;
};

function PatientDataProvider({ children }: PatientDataProviderProps) {
    const [addPatientFormData, setAddPatientFormData] = useState<NewPatientEntry | null>(null);

    const value: PatientDataContextType = {
        addPatientFormData,
        setAddPatientFormData
    };

    return <PatientDataContext.Provider value={value}>{children}</PatientDataContext.Provider>;
}

function usePatientData() {
    const context = useContext(PatientDataContext);
    if (!context) {
        throw new Error('usePatientData must be used within a PatientDataProvider');
    }
    return context;
}

export { PatientDataProvider, usePatientData };
