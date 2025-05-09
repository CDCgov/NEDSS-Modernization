import { PatientDemographicsSummary, PatientFileService } from 'generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number }
    | { status: 'complete'; summary: PatientDemographicsSummary }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number }
    | { type: 'complete'; summary: PatientDemographicsSummary }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id };
        case 'complete':
            return { status: 'complete', summary: action.summary };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePatientFileSummary = (id: number) => {
    const [state, dispatch] = useReducer(reducer, id ? { status: 'fetching', id } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientFileService.summary({ patient: id })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    return response
                        ? dispatch({ type: 'complete', summary: response })
                        : dispatch({ type: 'error', error: 'Failed to find patient summary' });
                });
        }
    }, [state.status]);

    const patient = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        summary: state.status === 'complete' ? state.summary : undefined,
        fetch: (id: number) => dispatch({ type: 'fetch', id })
    };

    return patient;
};
