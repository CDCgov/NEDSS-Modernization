import { useCallback } from 'react';
import { useLocation } from 'react-router';
import { BasicNewPatientEntry, initial } from './entry';
import { asBasicNewPatientEntry } from './asBasicNewPatientEntry';

type Interaction = {
    /**
     * Initializes the values of patient data entry based on the current Location state.
     *
     *  1. Use the {@code defaults} if present
     *  2. Use the {@code criteria.values} converted to patient entry defaults if present.
     *  3. Otherwise use the initial values for Patient data entry.
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
        if (location.state?.defaults) {
            //  first check the defaults passed when navigated to
            return { ...location.state?.defaults };
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
