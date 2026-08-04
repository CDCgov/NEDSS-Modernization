import { useContext } from 'react';

import { UserContext } from 'providers/UserContext';

const useUser = () => {
    const context = useContext(UserContext);

    if (context === undefined) {
        throw new Error('useUser must be used within a UserContextProvider');
    }

    return context;
};

export { useUser };
