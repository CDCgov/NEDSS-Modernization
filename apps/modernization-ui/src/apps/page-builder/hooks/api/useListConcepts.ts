import { Concept, ConceptControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; codesetName: string }
    | { status: 'complete'; concepts: Concept[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; codesetName: string }
    | { type: 'complete'; concepts: Concept[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', codesetName: action.codesetName };
        case 'complete':
            return { status: 'complete', concepts: action.concepts };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useListConcepts = (codesetName?: string) => {
    const [state, dispatch] = useReducer(
        reducer,
        codesetName ? { status: 'fetching', codesetName } : { status: 'idle' }
    );

    useEffect(() => {
        if (state.status === 'fetching') {
            ConceptControllerService.findConceptsUsingGet({
                authorization: authorization(),
                codeSetNm: state.codesetName
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', concepts: response ?? [] }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        concepts: state.status === 'complete' ? state.concepts : [],
        fetch: (codesetName: string) => dispatch({ type: 'fetch', codesetName })
    };

    return value;
};
