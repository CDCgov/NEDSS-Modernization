import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';
import { useFormContext } from 'react-hook-form';
import { asBasicNewPatientEntry } from 'apps/patient/add/basic/asBasicNewPatientEntry';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { found } = useSearchCriteriaEncrypted();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const add = () => {
        const defaults = asBasicNewPatientEntry(getValues());
        navigate('/patient/add', { state: { defaults, criteria: found } });
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
