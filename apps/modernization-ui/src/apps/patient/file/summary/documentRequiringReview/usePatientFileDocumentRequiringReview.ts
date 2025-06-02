import { DocumentRequiringReview, PatientFileService } from 'generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number }
    | { status: 'complete'; documents: DocumentRequiringReview[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number }
    | { type: 'complete'; documents: DocumentRequiringReview[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id };
        case 'complete':
            return { status: 'complete', documents: action.documents };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePatientFileDocumentRequiringReview = (id: number) => {
    const [state, dispatch] = useReducer(reducer, id ? { status: 'fetching', id } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            PatientFileService.documentsRequiringReview({ patient: id })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    return response
                        ? dispatch({ type: 'complete', documents: response })
                        : dispatch({ type: 'error', error: 'Failed to find patient documents requiring review' });
                });
        }
    }, [state.status]);

    const patient = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        documents: state.status === 'complete' ? state.documents : [],
        fetch: (id: number) => dispatch({ type: 'fetch', id })
    };

    return patient;
};
