import { useEffect, useReducer } from 'react';

import { Concept, ConceptControllerService, UpdateConceptRequest } from 'apps/page-builder/generated';

type State =
    | { status: 'idle' }
    | { status: 'updating'; valueset: string; localCode: string; request: UpdateConceptRequest }
    | { status: 'complete'; concept: Concept }
    | { status: 'error'; error: string };

type Action =
    | { type: 'update'; valueset: string; localCode: string; request: UpdateConceptRequest }
    | { type: 'complete'; concept: Concept }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'update':
            return {
                status: 'updating',
                valueset: action.valueset,
                localCode: action.localCode,
                request: action.request
            };
        case 'complete':
            return { status: 'complete', concept: action.concept };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useUpdateConcept = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'updating') {
            ConceptControllerService.updateConcept({
                codeSetNm: state.valueset,
                localCode: state.localCode,
                requestBody: state.request
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', concept: response });
                    } else {
                        dispatch({ type: 'error', error: 'Updated failed to return concept' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'updating',
        response: state.status === 'complete' ? state.concept : undefined,
        update: (valueset: string, localCode: string, request: UpdateConceptRequest) =>
            dispatch({ type: 'update', valueset, localCode, request })
    };

    return value;
};
