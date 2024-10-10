import { useNavigate } from 'react-router';
import { ExtendedNewPatientEntry } from './entry';
import { useExtendedTransformation } from './useExtendedTransformation';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { usePatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';
import { useConceptOptions } from 'options/concepts';
import { NewPatientEntry } from '..';

type Interaction = {
    add: (initial: any) => void;
};

const useBasicToExtended = (): Interaction => {
    const navigate = useNavigate();
    const nameCodes = usePatientNameCodedValues();
    const addressCodes = usePatientAddressCodedValues();
    const raceCategories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;

    const add = (initial: any) => {
        const defaults: ExtendedNewPatientEntry = useExtendedTransformation(
            initial,
            nameCodes,
            addressCodes,
            raceCategories
        );
        console.log({ defaults });
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    return { add };
};

export { useBasicToExtended };
