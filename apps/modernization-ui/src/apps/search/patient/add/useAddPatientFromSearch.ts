import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { asNewPatientEntry } from './asNewPatientEntry';
import { useFormContext } from 'react-hook-form';
import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const { criteria } = useSearchCriteriaEncrypted();

    const add = () => {
        console.log('add');
        console.log(criteria);
        const defaults = asNewPatientEntry(getValues());
        navigate('/add-patient', { state: { defaults, criteria } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
