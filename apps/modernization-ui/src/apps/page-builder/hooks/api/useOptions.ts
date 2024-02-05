import { authorization } from 'authorization';
import { ConceptOptionsService, Option } from 'generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; name: string }
    | { status: 'complete'; options: Option[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; name: string }
    | { type: 'complete'; options: Option[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', name: action.name };
        case 'complete':
            return { status: 'complete', options: action.options };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useOptions = (name?: string) => {
    const [state, dispatch] = useReducer(reducer, name ? { status: 'fetching', name } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            ConceptOptionsService.allUsingGet({ authorization: authorization(), name: state.name })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', options: response?.options ?? [] }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        options: state.status === 'complete' ? state.options : [],
        fetch: (name: string) => dispatch({ type: 'fetch', name })
    };

    return value;
};
