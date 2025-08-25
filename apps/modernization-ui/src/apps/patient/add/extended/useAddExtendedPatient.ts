import { useCallback, useEffect, useMemo, useReducer, useState } from 'react';
import { AddPatientState, useAddPatient, creator } from 'apps/patient/add';
import {
    AddExtendedPatientInteraction,
    AddExtendedPatientState,
    SubFormDirtyState,
    ValidationErrors
} from './useAddExtendedPatientInteraction';
import { PatientDemographicsEntry, transformer } from 'libs/patient/demographics';

type ExtendedStep =
    | { status: 'validating'; entry: PatientDemographicsEntry }
    | { status: 'revalidating' }
    | { status: 'valid'; entry: PatientDemographicsEntry }
    | { status: 'invalid'; validationErrors: ValidationErrors }
    | { status: 'waiting' };

type ExtendedAction =
    | { type: 'validate'; entry: PatientDemographicsEntry }
    | { type: 'revalidate' }
    | { type: 'invalidate'; validationErrors: ValidationErrors }
    | { type: 'validated' };

const initial: ExtendedStep = { status: 'waiting' };

const reducer = (current: ExtendedStep, action: ExtendedAction): ExtendedStep => {
    switch (action.type) {
        case 'validate': {
            return { status: 'validating', entry: action.entry };
        }
        case 'revalidate': {
            return current.status === 'invalid' ? { status: 'revalidating' } : initial;
        }
        case 'invalidate': {
            return { status: 'invalid', validationErrors: action.validationErrors };
        }
        case 'validated': {
            return current.status === 'validating' ? { status: 'valid', entry: current.entry } : current;
        }
        default: {
            return current;
        }
    }
};

const useAddExtendedPatient = (): AddExtendedPatientInteraction => {
    const addPatient = useAddPatient({ transformer, creator });

    const [step, dispatch] = useReducer(reducer, initial);
    const [subFormDirtyState, setSubFormDirtyState] = useState<SubFormDirtyState>({
        address: false,
        phone: false,
        identification: false,
        name: false,
        race: false
    });

    const setSubFormState = (subFormState: Partial<SubFormDirtyState>) => {
        setSubFormDirtyState((current) => ({ ...current, ...subFormState }));
        dispatch({ type: 'revalidate' });
    };

    useEffect(() => {
        if (step.status === 'validating' || step.status === 'revalidating') {
            if (
                subFormDirtyState.address ||
                subFormDirtyState.identification ||
                subFormDirtyState.name ||
                subFormDirtyState.phone ||
                subFormDirtyState.race
            ) {
                dispatch({ type: 'invalidate', validationErrors: { dirtySections: subFormDirtyState } });
            } else {
                dispatch({ type: 'validated' });
            }
        } else if (step.status === 'valid') {
            addPatient.create(step.entry);
        }
    }, [step.status, dispatch, addPatient.create]);

    const state: AddExtendedPatientState = useMemo(() => evaluateState(step, addPatient), [step, addPatient]);

    const create = useCallback((entry: PatientDemographicsEntry) => dispatch({ type: 'validate', entry }), [dispatch]);

    return {
        ...state,
        create,
        setSubFormState
    };
};

const evaluateState = (step: ExtendedStep, state: AddPatientState): AddExtendedPatientState => {
    switch (step.status) {
        case 'validating': {
            return { status: 'working' };
        }
        case 'invalid': {
            return { status: 'invalid', validationErrors: step.validationErrors };
        }
        default: {
            return state;
        }
    }
};

export { useAddExtendedPatient };
