import { AddPatientInteraction, useAddPatient, creator } from 'apps/patient/add';
import { BasicNewPatientEntry } from './entry';
import { transformer } from './transformer';
import { useState } from 'react';

type AddBasicPatientInteraction = AddPatientInteraction<BasicNewPatientEntry> & {
    canSave: boolean;
    setCanSave: (canSave: boolean) => void;
};

/**
 * Allows creation of a patient from a BasicNewPatientEntry object.
 *
 * @return {AddPatientInteraction}
 */
const useAddBasicPatient = (): AddBasicPatientInteraction => {
    const interaction = useAddPatient({ transformer, creator });
    const [allowSave, setAllowSave] = useState(true);

    return {
        ...interaction,
        canSave: allowSave,
        setCanSave: setAllowSave
    };
};

export { useAddBasicPatient };
