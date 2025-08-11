import { today } from 'date';
import { useLocationDefaults } from 'libs/location';
import { PatientDemographicsDefaults } from './demographics';

const usePatientDemographicDefaults = (): PatientDemographicsDefaults => {
    const location = useLocationDefaults();

    return { asOf: today, address: location };
};

export { usePatientDemographicDefaults };
