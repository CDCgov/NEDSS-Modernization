import { useCallback, useEffect, useMemo, useReducer, useState } from 'react';
import { CreatedPatient, NewPatient, Creator, Transformer } from 'apps/patient/add/api';
import { ExtendedNewPatientEntry } from './entry';
import {
    AddExtendedPatientInteraction,
    AddExtendedPatientState,
    SubFormDirtyState,
    ValidationErrors
} from './useAddExtendedPatientInteraction';

type ExtendedStep =
    | { status: 'validating'; entry: ExtendedNewPatientEntry }
    | { status: 'requesting'; entry: ExtendedNewPatientEntry }
    | { status: 'creating'; input: NewPatient }
    | { status: 'created'; created: CreatedPatient }
    | { status: 'invalid'; validationErrors: ValidationErrors }
    | { status: 'waiting' };

type ExtendedAction =
    | { type: 'validate'; entry: ExtendedNewPatientEntry }
    | { type: 'request' }
    | { type: 'invalidate'; validationErrors: ValidationErrors }
    | { type: 'create'; input: NewPatient }
    | { type: 'complete'; created: CreatedPatient }
    | { type: 'wait' };

const initial: ExtendedStep = { status: 'waiting' };

const reducer = (current: ExtendedStep, action: ExtendedAction): ExtendedStep => {
    switch (action.type) {
        case 'validate': {
            return { status: 'validating', entry: action.entry };
        }
        case 'invalidate': {
            return { status: 'invalid', validationErrors: action.validationErrors };
        }
        case 'request': {
            return current.status === 'validating' ? { status: 'requesting', entry: current.entry } : current;
        }
        case 'create': {
            return { status: 'creating', input: action.input };
        }
        case 'complete': {
            return { status: 'created', created: action.created };
        }
        default: {
            return current;
        }
    }
};

type Settings = {
    transformer: Transformer<ExtendedNewPatientEntry>;
    creator: Creator;
};

const useAddExtendedPatient = ({ transformer, creator }: Settings): AddExtendedPatientInteraction => {
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
    };

    useEffect(() => {
        if (step.status === 'validating') {
            if (
                !subFormDirtyState.address &&
                !subFormDirtyState.identification &&
                !subFormDirtyState.name &&
                !subFormDirtyState.phone &&
                !subFormDirtyState.race
            ) {
                dispatch({ type: 'request' });
            } else {
                dispatch({ type: 'invalidate', validationErrors: { dirtySections: subFormDirtyState } });
            }
        } else if (step.status === 'requesting') {
            const input = transformer(step.entry);
            dispatch({ type: 'create', input });
        } else if (step.status === 'creating') {
            creator(step.input).then((created) => dispatch({ type: 'complete', created }));
        }
    }, [step.status, dispatch]);

    const state: AddExtendedPatientState = useMemo(() => evaluateState(step), [step]);

    const create = useCallback((entry: ExtendedNewPatientEntry) => dispatch({ type: 'validate', entry }), [dispatch]);

    return {
        ...state,
        create,
        setSubFormState
    };
};

const evaluateState = (step: ExtendedStep): AddExtendedPatientState => {
    switch (step.status) {
        case 'creating':
        case 'validating':
        case 'requesting': {
            return { status: 'working' };
        }
        case 'created': {
            return { status: 'created', created: step.created };
        }
        case 'invalid': {
            return { status: 'invalid', validationErrors: step.validationErrors };
        }
        default: {
            return { status: step.status };
        }
    }
};

export { useAddExtendedPatient };
export type { Settings, Transformer, Creator };
