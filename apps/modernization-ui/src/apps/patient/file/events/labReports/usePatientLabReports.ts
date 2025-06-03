import { PatientLabReport, PatientLabReportsService } from 'generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number }
    | { status: 'complete'; patientLabReports: PatientLabReport[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number }
    | { type: 'complete'; patientLabReports: PatientLabReport[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id };
        case 'complete':
            return { status: 'complete', patientLabReports: action.patientLabReports };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePatientLabReports = (id: number) => {
    const [state, dispatch] = useReducer(reducer, id ? { status: 'fetching', id } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientLabReportsService.patientLabReports({ patientId: id })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    return response
                        ? dispatch({ type: 'complete', patientLabReports: response })
                        : dispatch({ type: 'error', error: 'Failed to find Lab Reports related to patient.' });
                });
        }
    }, [state.status]);

    const patient = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        patientLabReports: state.status === 'complete' ? state.patientLabReports : undefined,
        fetch: (id: number) => dispatch({ type: 'fetch', id })
    };

    return patient;
};
