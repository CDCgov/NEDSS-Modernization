import { useEffect, useReducer } from 'react';
import { PatientInvestigation, PatientInvestigationsService } from 'generated';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number }
    | { status: 'complete'; data: PatientInvestigation[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number }
    | { type: 'complete'; data: PatientInvestigation[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id };
        case 'complete':
            return { status: 'complete', data: action.data };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePatientInvestigations = (patientId: number) => {
    const [state, dispatch] = useReducer(
        reducer,
        patientId ? { status: 'fetching', id: patientId } : { status: 'idle' }
    );

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientInvestigationsService.patientInvestigations({ patientId: state.id })
                .catch((err) => dispatch({ type: 'error', error: err.message }))
                .then((result) => {
                    return result
                        ? dispatch({ type: 'complete', data: result })
                        : dispatch({ type: 'error', error: 'Failed to fetch investigation data' });
                });
        }
    }, [state.status]);

    const investigations = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        data: state.status === 'complete' ? state.data : undefined,
        fetch: (id: number) => dispatch({ type: 'fetch', id })
    };

    return investigations;
};
