import { useCallback, useEffect, useMemo, useReducer, useState } from 'react';
import { CreatedPatient, NewPatient, Creator, Transformer } from 'apps/patient/add/api';
import { ExtendedNewPatientEntry } from './entry';
import {
    AddExtendedPatientInteraction,
    Working,
    Created,
    Invalid,
    SubFormDirtyState,
    ValidationErrors
} from './useAddExtendedPatientInteraction';

type Step =
    | { status: 'requesting'; entry: ExtendedNewPatientEntry }
    | { status: 'creating'; input: NewPatient }
    | { status: 'created'; created: CreatedPatient }
    | { status: 'invalid'; validationErrors: ValidationErrors }
    | { status: 'waiting' };

type Action =
    | { type: 'request'; entry: ExtendedNewPatientEntry }
    | { type: 'invalidate'; validationErrors: ValidationErrors }
    | { type: 'create'; input: NewPatient }
    | { type: 'complete'; created: CreatedPatient }
    | { type: 'wait' };

const initial: Step = { status: 'waiting' };

const reducer = (current: Step, action: Action): Step => {
    switch (action.type) {
        case 'request': {
            return { status: 'requesting', entry: action.entry };
        }
        case 'invalidate': {
            return { status: 'invalid', validationErrors: action.validationErrors };
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

type State = Working | Created | Invalid;

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
        setSubFormDirtyState((current) => {
            return { ...current, ...subFormState };
        });
    };

    useEffect(() => {
        if (step.status === 'requesting') {
            if (
                !subFormDirtyState.address &&
                !subFormDirtyState.identification &&
                !subFormDirtyState.name &&
                !subFormDirtyState.phone &&
                !subFormDirtyState.race
            ) {
                const input = transformer(step.entry);
                dispatch({ type: 'create', input });
            } else {
                dispatch({ type: 'invalidate', validationErrors: { dirtySections: subFormDirtyState } });
            }
        } else if (step.status === 'creating') {
            creator(step.input).then((created) => dispatch({ type: 'complete', created }));
        }
    }, [step.status, dispatch]);

    const state: State = useMemo(() => evaluateState(step), [step]);

    const create = useCallback((entry: ExtendedNewPatientEntry) => dispatch({ type: 'request', entry }), [dispatch]);

    return {
        ...state,
        create,
        setSubFormState
    };
};

const evaluateState = (step: Step): State => {
    switch (step.status) {
        case 'creating':
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
