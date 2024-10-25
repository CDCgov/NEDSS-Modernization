import { AddPatientInteraction, useAddPatient, creator } from 'apps/patient/add';
import { BasicNewPatientEntry } from './entry';
import { transformer } from './transformer';

/**
 * Allows creation of a patient from a BasicNewPatientEntry object.
 *
 * @return {AddPatientInteraction}
 */
const useAddBasicPatient = (): AddPatientInteraction<BasicNewPatientEntry> => {
    const interaction = useAddPatient({ transformer, creator });

    return interaction;
};

export { useAddBasicPatient };
