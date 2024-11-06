import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { asNewPatientEntry } from './asNewPatientEntry';
import { useFormContext } from 'react-hook-form';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const add = () => {
        const defaults = asNewPatientEntry(getValues());
        navigate('/add-patient', { state: { defaults } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
