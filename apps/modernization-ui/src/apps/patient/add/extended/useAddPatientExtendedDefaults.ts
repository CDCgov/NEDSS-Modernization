import { useCallback } from 'react';
import { useLocation } from 'react-router-dom';
import { ExtendedNewPatientEntry, initial as initialEntry } from './entry';

type Interaction = {
    /** provides the default values for Extended New Patient Entry  */
    initialize: () => ExtendedNewPatientEntry;
};

/**
 * Provides a function to initialize the default form values for New patient - extended.
 *
 * @return {Interaction} to initialize the default state of ExtendedNewPatientEntry
 */
const useAddPatientExtendedDefaults = (): Interaction => {
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

export { useAddPatientExtendedDefaults };
