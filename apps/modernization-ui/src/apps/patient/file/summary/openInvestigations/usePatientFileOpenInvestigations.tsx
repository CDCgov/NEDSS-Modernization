import { PatientInvestigation, PatientOpenInvestigationsService } from 'generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number }
    | { status: 'complete'; patientOpenInvestigations: PatientInvestigation[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number }
    | { type: 'complete'; patientOpenInvestigations: PatientInvestigation[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id };
        case 'complete':
            return { status: 'complete', patientOpenInvestigations: action.patientOpenInvestigations };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePatientFileOpenInvestigations = (id: number) => {
    const [state, dispatch] = useReducer(reducer, id ? { status: 'fetching', id } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientOpenInvestigationsService.patientOpenInvestigations({ patientId: id })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    return response
                        ? dispatch({ type: 'complete', patientOpenInvestigations: response })
                        : dispatch({ type: 'error', error: 'Failed to find Open Investigations related to patient.' });
                });
        }
    }, [state.status]);

    const patient = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        patientOpenInvestigations: state.status === 'complete' ? state.patientOpenInvestigations : undefined,
        fetch: (id: number) => dispatch({ type: 'fetch', id })
    };

    return patient;
};
