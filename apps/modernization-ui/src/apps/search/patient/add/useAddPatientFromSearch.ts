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
        const defaults = asNewPatientEntry(getValues());
        navigate('/patient/add', { state: { defaults, criteria: found } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
