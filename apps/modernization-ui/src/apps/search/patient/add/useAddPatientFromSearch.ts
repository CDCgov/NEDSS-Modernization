import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { asNewPatientEntry } from './asNewPatientEntry';
import { useFormContext } from 'react-hook-form';
import { useConfiguration } from 'configuration';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const { features } = useConfiguration();
    const addPatientUrl = features.patient.add.enabled ? '/patient/add' : '/add-patient';

    const add = () => {
        const defaults = asNewPatientEntry(getValues());
        navigate(addPatientUrl, { state: { defaults } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
