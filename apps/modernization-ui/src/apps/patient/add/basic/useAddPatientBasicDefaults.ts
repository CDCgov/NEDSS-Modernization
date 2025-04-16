import { useCallback } from 'react';
import { useLocation } from 'react-router';
import { BasicNewPatientEntry, initial } from './entry';
import { asBasicNewPatientEntry } from './asBasicNewPatientEntry';

type Interaction = {
    /**
     * Initializes the values of basic patient data entry based on the current Location state.
     *
     *  1. Use the {@code basic} if present
     *  2. Use the {@code criteria.values} converted to patient entry defaults if present.
     *  3. Otherwise use the initial values for basic patient data entry.
     *
     * @return  {BasicNewPatientEntry} The defaults values.
     */
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
        if (location.state?.basic) {
            //  first check if basic was passed during navigation
            return { ...location.state?.basic };
        } else if (location.state?.criteria?.values) {
            //  convert the search criteria values to
            return asBasicNewPatientEntry(initial())({ ...location.state?.criteria?.values });
        } else {
            return initial();
        }
    }, [location.state?.defaults]);

    return { initialize };
};

export { useAddPatientBasicDefaults };
