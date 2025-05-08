import { createContext, useContext, useEffect } from 'react';
import { PatientDemographicsSummary } from 'generated';
import { usePatientFileSummary } from './usePatientFileSummary';

// Define the context type
interface PatientFileContextType {
    changed: () => void;
    summary?: PatientDemographicsSummary;
}

// Create the context with an initial value of null
export const PatientFileContext = createContext<PatientFileContextType | undefined>(undefined);

// Create a custom hook to access the context
export function usePatientFileContext() {
    const context = useContext(PatientFileContext);
    if (context === undefined) {
        throw new Error('useProfileContext must be used within a ProfileProvider');
    }
    return context;
}

export const PatientFileProvider = ({ children, id }: any) => {
    const { summary, fetch } = usePatientFileSummary(id);

    useEffect(() => {
        if (id) {
            fetch(id);
        }
    }, [id]);

    const changed = () => fetch(id);

    return <PatientFileContext.Provider value={{ changed, summary }}>{children}</PatientFileContext.Provider>;
};
