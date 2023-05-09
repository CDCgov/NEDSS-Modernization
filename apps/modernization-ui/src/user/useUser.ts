import { UserContext } from 'providers/UserContext';
import { useContext } from 'react';

const useUser = () => {
    const context = useContext(UserContext);

    if (context === undefined) {
        throw new Error('usePage must be used within a UserContextProvider');
    }

    return context;
};

export { useUser };
