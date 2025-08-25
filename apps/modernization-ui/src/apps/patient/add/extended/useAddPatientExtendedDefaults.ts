import { initial, PatientDemographicsEntry, usePatientDemographicDefaults } from 'libs/patient/demographics';
import { useCallback } from 'react';
import { useLocation } from 'react-router';

type Interaction = {
    /**
     * Initializes the values of extended patient data entry based on the current Location state.
     *
     *  1. Use the {@code extended} if present
     *  2. Otherwise use the initial values for Extended patient data entry.
     *
     * @return  {PatientDemographicsEntry} The defaults values.
     */
    initialize: () => PatientDemographicsEntry;
};

/**
 * Provides a function to initialize the default form values for New patient - extended.
 *
 * @return {Interaction} to initialize the default state of ExtendedNewPatientEntry
 */
const useAddPatientExtendedDefaults = (): Interaction => {
    const location = useLocation();
    const defaults = usePatientDemographicDefaults();

    const initialize = useCallback(() => {
        if (location.state?.extended) {
            return location.state?.extended;
        } else {
            return initial(defaults);
        }
    }, [location.state?.defaults]);

    return { initialize };
};

export { useAddPatientExtendedDefaults };
