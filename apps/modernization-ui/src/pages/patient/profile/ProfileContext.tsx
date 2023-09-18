import { createContext, useContext } from 'react';
import { usePatientProfile } from './usePatientProfile';
import { ApolloQueryResult, OperationVariables } from '@apollo/client';
import { PatientProfileResult } from './useFindPatientProfileSummary';

// Define the context type
interface ProfileContextType {
    changed: (variables?: Partial<OperationVariables> | undefined) => Promise<ApolloQueryResult<PatientProfileResult>>;
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
    const { refetch: changed } = usePatientProfile(id);

    return <ProfileContext.Provider value={{ changed }}>{children}</ProfileContext.Provider>;
};
