import { createContext, useContext } from 'react';
import { usePatientFile, Patient } from './patientData';
import { PatientSummary } from 'generated/graphql/schema';

// Define the context type
interface PatientFileContextType {
    changed: () => void;
    patient?: Patient;
    summary?: PatientSummary;
}

// Create the context with an initial value of null
export const PatientFileContext = createContext<PatientFileContextType | undefined>(undefined);

// Create a custom hook to access the context
export function usePatientFileContext() {
    const context = useContext(PatientFileContext);
    if (context === undefined) {
        throw new Error('usePatientFileContext must be used within a PatientFileProvider');
    }
    return context;
}

export const PatientFileProvider = ({ children, id }: any) => {
    const { patient, summary, refetch } = usePatientFile(id);

    const changed = () => refetch();

    return <PatientFileContext.Provider value={{ changed, summary, patient }}>{children}</PatientFileContext.Provider>;
};
