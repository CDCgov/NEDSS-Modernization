import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';
import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';
import { useFormContext } from 'react-hook-form';
import { asBasicNewPatientEntry } from 'apps/patient/add/basic/asBasicNewPatientEntry';
import { useConfiguration } from 'configuration';
import { asNewPatientEntry } from './asNewPatientEntry';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { found } = useSearchCriteriaEncrypted();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const { features } = useConfiguration();

    const addBasic = (criteria: Partial<PatientCriteriaEntry>) => {
        const defaults = asBasicNewPatientEntry(criteria);
        navigate('/patient/add', { state: { defaults, criteria: found } });
    };

    const addNew = (criteria: Partial<PatientCriteriaEntry>) => {
        const defaults = asNewPatientEntry(criteria);
        navigate('/add-patient', { state: { defaults, criteria: found } });
    };

    const add = () => {
        const criteria = getValues();

        if (features.patient.add.enabled) {
            addBasic(criteria);
        } else {
            addNew(criteria);
        }
    };

    return {
        add
    };
};

export { useAddPatientFromSearch };
