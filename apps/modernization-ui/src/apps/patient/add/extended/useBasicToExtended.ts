import { useNavigate } from 'react-router';
import { ExtendedNewPatientEntry } from './entry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { useConceptOptions } from 'options/concepts';
import { NewPatientEntry } from '..';
import { asExtendedNewPatientEntry } from './asExtendedNewPatientEntry';

type Interaction = {
    add: (initial: NewPatientEntry) => void;
};

const useBasicToExtended = (): Interaction => {
    const navigate = useNavigate();
    const nameCodes = usePatientNameCodedValues();
    const raceCategories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;

    const add = (initial: NewPatientEntry) => {
        const defaults: ExtendedNewPatientEntry = asExtendedNewPatientEntry(initial, nameCodes, raceCategories);
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    return { add };
};

export { useBasicToExtended };