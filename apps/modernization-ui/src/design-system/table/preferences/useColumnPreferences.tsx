import { useContext } from 'react';
import { ColumnPreferenceContext, Interaction } from './ColumnPreferenceProvider';

const useColumnPreferences = (): Interaction => {
    const context = useContext(ColumnPreferenceContext);

    if (context === undefined) {
        throw new Error('useColumnPreferences must be used within a ColumnPreferenceProvider');
    }

    return context;
};

const useMaybeColumnPreferences = (): Interaction | undefined => {
    const context = useContext(ColumnPreferenceContext);

    if (context === undefined) {
        return undefined;
    }

    return context;
};

export { useColumnPreferences, useMaybeColumnPreferences };
