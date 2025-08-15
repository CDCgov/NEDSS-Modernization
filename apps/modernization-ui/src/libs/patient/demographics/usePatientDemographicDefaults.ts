import { today } from 'date';
import { PatientDemographicsDefaults } from './demographics';

const usePatientDemographicDefaults = (): PatientDemographicsDefaults => {
    return { asOf: today, address: { country: { value: '840', name: 'United States' } } };
};

export { usePatientDemographicDefaults };
