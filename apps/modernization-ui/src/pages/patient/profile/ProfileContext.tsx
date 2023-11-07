import { createContext, useContext } from 'react';
import { usePatientProfile } from './usePatientProfile';
import { PatientSummary } from 'generated/graphql/schema';

// Define the context type
interface ProfileContextType {
    changed: () => void;
    summary?: PatientSummary;
}

// Create the context with an initial value of null
export const ProfileContext = createContext<ProfileContextType | undefined>(undefined);

// Create a custom hook to access the context
export function useProfileContext() {
    const context = useContext(ProfileContext);
    if (context === undefined) {
        throw new Error('useProfileContext must be used within a ProfileProvider');
    }
    return context;
}

export const ProfileProvider = ({ children, id }: any) => {
    const { refetch, summary } = usePatientProfile(id);

    const changed = () => refetch();

    return <ProfileContext.Provider value={{ changed, summary }}>{children}</ProfileContext.Provider>;
};
