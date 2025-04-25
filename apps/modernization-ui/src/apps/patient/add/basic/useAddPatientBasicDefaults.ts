import { useMemo } from 'react';
import { useLocation } from 'react-router';
import { BasicNewPatientEntry, initial } from './entry';
import { asBasicNewPatientEntry } from './asBasicNewPatientEntry';

type Interaction = {
    defaults: BasicNewPatientEntry | undefined;
};

/**
 * Provides any defaulted values of basic patient data entry based on the Location state.  Values are currently
 * defaulted in two ways; from Patient search criteria, or from basic patient entry values that are stored when
 * transitioning to extended patient entry.
 *
 *  1. Use the {@code basic} if present
 *  2. Use the {@code criteria.values} converted to patient entry defaults if present.
 *
 * @return {Interaction} to initialize the default state of BasicNewPatientEntry
 */
const useAddPatientBasicDefaults = (): Interaction => {
    const location = useLocation();

    const defaults = useMemo(() => {
        if (location.state?.basic) {
            //  first check if basic was passed during navigation
            return { ...location.state?.basic } as BasicNewPatientEntry;
        } else if (location.state?.criteria?.values) {
            //  convert the search criteria values to
            return asBasicNewPatientEntry(initial())({ ...location.state?.criteria?.values });
        }
    }, [location.state?.basic, location.state?.criteria?.values]);

    return { defaults };
};

export { useAddPatientBasicDefaults };
