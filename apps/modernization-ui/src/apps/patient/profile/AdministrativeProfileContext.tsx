import { createContext, useContext } from 'react';
import { useAdministrativePatientProfile } from './useAdministrativePatientProfile';
import { PatientProfileAdministrativeResult } from './administrative/useFindPatientProfileAdministrative';

// Define the context type
interface AdministrativeProfileContextType {
    changed: () => void;
    summary?: PatientProfileAdministrativeResult;
}

// Create the context with an initial value of null
export const AdministrativeProfileContext = createContext<AdministrativeProfileContextType | undefined>(undefined);

// Create a custom hook to access the context
export function useAdministrativeProfileContext() {
    const context = useContext(AdministrativeProfileContext);
    if (context === undefined) {
        throw new Error('useAdministrativeProfileContext must be used within a AdministrativeProfileProvider');
    }
    return context;
}

export const AdministrativeProfileProvider = ({ children, id }: any) => {
    const { refetch, summary } = useAdministrativePatientProfile(id);

    const changed = () => refetch();

    return (
        <AdministrativeProfileContext.Provider value={{ changed, summary }}>
            {children}
        </AdministrativeProfileContext.Provider>
    );
};
