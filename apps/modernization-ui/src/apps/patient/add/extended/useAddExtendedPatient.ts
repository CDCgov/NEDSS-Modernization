import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { CreatedPatient, NewPatient } from './api';
import { ExtendedNewPatientEntry } from './entry';

type Step =
    | { status: 'requesting'; entry: ExtendedNewPatientEntry }
    | { status: 'creating'; input: NewPatient }
    | { status: 'created'; created: CreatedPatient }
    | { status: 'waiting' };

type Action =
    | { type: 'request'; entry: ExtendedNewPatientEntry }
    | { type: 'create'; input: NewPatient }
    | { type: 'complete'; created: CreatedPatient }
    | { type: 'wait' };

const initial: Step = { status: 'waiting' };

const reducer = (current: Step, action: Action): Step => {
    switch (action.type) {
        case 'request': {
            return { status: 'requesting', entry: action.entry };
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

type Transformer = (entry: ExtendedNewPatientEntry) => NewPatient;
type Creator = (input: NewPatient) => Promise<CreatedPatient>;

type Settings = {
    transformer: Transformer;
    creator: Creator;
};

type Working = {
    status: 'waiting' | 'working';
};

type Created = {
    status: 'created';
    created: CreatedPatient;
};

type State = Working | Created;

type Interaction = {
    create: (entry: ExtendedNewPatientEntry) => void;
};

type Return = Interaction & State;

const useAddExtendedPatient = ({ transformer, creator }: Settings): Return => {
    const [step, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (step.status === 'requesting') {
            const input = transformer(step.entry);
            dispatch({ type: 'create', input });
        } else if (step.status === 'creating') {
            creator(step.input).then((created) => dispatch({ type: 'complete', created }));
        }
    }, [step.status, dispatch]);

    const state: State = useMemo(() => evaluateState(step), [step]);

    const create = useCallback((entry: ExtendedNewPatientEntry) => dispatch({ type: 'request', entry }), [dispatch]);

    return {
        ...state,
        create
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
        default: {
            return { status: step.status };
        }
    }
};

export { useAddExtendedPatient };
export type { Settings, Transformer, Creator };
