import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { maybeMap } from 'utils/mapping';
import { PatientInvestigationsService, PatientInvestigation as PatientInvestigationResponse } from 'generated';
import { PatientInvestigation } from './PatientInvestigation';

type State<I, O> =
    | { status: 'idle' }
    | { status: 'fetching'; input: I }
    | { status: 'complete'; data: O }
    | { status: 'error'; error: string };

type Action<I, O> = { type: 'fetch'; input: I } | { type: 'complete'; data: O } | { type: 'error'; error: string };

const reducer = <I, O>(_current: State<I, O>, action: Action<I, O>): State<I, O> => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', input: action.input };
        case 'complete':
            return { status: 'complete', data: action.data };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

type Interaction<I, O> = {
    error?: string;
    isLoading: boolean;
    data: O;
    load: (input: I) => void;
};

type Request = { patientId: number };

const usePatientInvestigations = (patientId: number): Interaction<Request, PatientInvestigation[]> => {
    const [state, dispatch] = useReducer(reducer<Request, PatientInvestigation[]>, {
        status: 'fetching',
        input: { patientId }
    });

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientInvestigationsService.patientInvestigations(state.input)
                .catch((err) => dispatch({ type: 'error', error: err.message }))
                .then((response) => response ?? [])
                .then((response) => response.map(transform))
                .then((result) => dispatch({ type: 'complete', data: result }));
        }
    }, [state.status]);

    const load = useCallback((input: Request) => dispatch({ type: 'fetch', input }), [dispatch]);

    const interaction = useMemo(
        () => ({
            error: state.status === 'error' ? state.error : undefined,
            isLoading: state.status === 'fetching',
            data: state.status === 'complete' ? state.data : [],
            load
        }),
        [state, load]
    );

    return interaction;
};

const maybeDate = maybeMap((value: string) => new Date(value));

const transform = (value: PatientInvestigationResponse): PatientInvestigation => ({
    ...value,
    startedOn: maybeDate(value.startedOn)
});

export { usePatientInvestigations };
