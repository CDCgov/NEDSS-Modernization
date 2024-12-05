import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { asNewPatientEntry } from './asNewPatientEntry';
import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';
import { useFormContext } from 'react-hook-form';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { found } = useSearchCriteriaEncrypted();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const add = () => {
        console.log('add');
        console.log(found);
        const defaults = asNewPatientEntry(getValues());
        navigate('/add-patient', { state: { defaults, criteria: found } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
