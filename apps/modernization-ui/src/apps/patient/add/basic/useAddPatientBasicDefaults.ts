import { useCallback } from 'react';
import { useLocation } from 'react-router-dom';
import { BasicNewPatientEntry, initial as initialEntry } from './entry';

type Interaction = {
    initialize: () => BasicNewPatientEntry;
};

/**
 * Provides a function to initialize the default form values for New patient - basic.
 *
 * @return {Interaction} to initialize the default state of BasicNewPatientEntry
 */
const useAddPatientBasicDefaults = (): Interaction => {
    const location = useLocation();

    const initialize = useCallback(() => {
        if (location.state?.defaults) {
            return location.state?.defaults;
        } else {
            return initialEntry();
        }
    }, [location.state?.defaults]);

    return { initialize };
};

export { useAddPatientBasicDefaults };
