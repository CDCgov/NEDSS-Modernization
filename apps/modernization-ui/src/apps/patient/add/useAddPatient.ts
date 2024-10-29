import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { CreatedPatient, Creator, NewPatient, Transformer } from './api';

type Step<I> =
    | { status: 'requesting'; entry: I }
    | { status: 'creating'; input: NewPatient }
    | { status: 'created'; created: CreatedPatient }
    | { status: 'waiting' };

type Action<I> =
    | { type: 'request'; entry: I }
    | { type: 'create'; input: NewPatient }
    | { type: 'complete'; created: CreatedPatient }
    | { type: 'wait' };

const reducer = <I>(current: Step<I>, action: Action<I>): Step<I> => {
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

type Working = {
    status: 'waiting' | 'working';
};

type Created = {
    status: 'created';
    created: CreatedPatient;
};

type AddPatientState = Working | Created;

type AddPatientInteraction<I> = AddPatientState & {
    create: (entry: I) => void;
};

type AddPatientSettings<E> = {
    transformer: Transformer<E>;
    creator: Creator;
};

/**
 * Allows creation of a patient from an entry object.
 *
 * By default the status will be "waiting", when create is invoked the status will change to
 * "working" until the patient has been created.  Once patient is created the status will
 * change to "created" and the "created" property will contain the CreatedPatient.
 *
 * @return {AddPatientInteraction}
 */
const useAddPatient = <E>({ transformer, creator }: AddPatientSettings<E>): AddPatientInteraction<E> => {
    const [step, dispatch] = useReducer(reducer<E>, { status: 'waiting' });

    useEffect(() => {
        if (step.status === 'requesting') {
            const input = transformer(step.entry);
            dispatch({ type: 'create', input });
        } else if (step.status === 'creating') {
            creator(step.input).then((created) => dispatch({ type: 'complete', created }));
        }
    }, [step.status, dispatch]);

    const state: AddPatientState = useMemo(() => evaluateState(step), [step]);

    const create = useCallback((entry: E) => dispatch({ type: 'request', entry }), [dispatch]);

    return {
        ...state,
        create
    };
};

const evaluateState = <I>(step: Step<I>): AddPatientState => {
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

export type { AddPatientState, AddPatientInteraction, AddPatientSettings, Working, Created };
export { useAddPatient };
