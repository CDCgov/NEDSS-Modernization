import { createContext, useContext } from 'react';
import { Profile, usePatientProfile } from './usePatientProfile';

// Define the context type
interface ProfileContextType {
    changed: () => void;
    profile: Profile | undefined;
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
    const { refetch, profile } = usePatientProfile(id);

    const changed = () => refetch();

    return <ProfileContext.Provider value={{ changed, profile }}>{children}</ProfileContext.Provider>;
};
