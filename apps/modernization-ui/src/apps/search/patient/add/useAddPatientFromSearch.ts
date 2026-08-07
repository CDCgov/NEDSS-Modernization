import { useCallback } from 'react';

import { useFormContext } from 'react-hook-form';
import { useNavigate } from 'react-router';

import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';

import { PatientCriteriaEntry } from '../criteria';

type Interaction = {
    add: () => void;
};

const useAddPatientFromSearch = (): Interaction => {
    const navigate = useNavigate();
    const { found } = useSearchCriteriaEncrypted();
    const { getValues } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const add = useCallback(() => {
        const criteria = getValues();

        navigate('/patient/add', { state: { criteria: { encrypted: found, values: criteria } } });
    }, [getValues, found, navigate]);

    return {
        add,
    };
};

export { useAddPatientFromSearch };
