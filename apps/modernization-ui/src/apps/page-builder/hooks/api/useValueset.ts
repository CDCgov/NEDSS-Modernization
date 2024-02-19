import { Valueset, ValueSetControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; name: string }
    | { status: 'complete'; valueset: Valueset }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; name: string }
    | { type: 'complete'; valueset: Valueset }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', name: action.name };
        case 'complete':
            return { status: 'complete', valueset: action.valueset };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useValueset = (name?: string) => {
    const [state, dispatch] = useReducer(reducer, name ? { status: 'fetching', name } : { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            ValueSetControllerService.getValuesetUsingGet({ authorization: authorization(), codeSetNm: state.name })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    return response
                        ? dispatch({ type: 'complete', valueset: response })
                        : dispatch({ type: 'error', error: 'Failed to find valueset' });
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        valueset: state.status === 'complete' ? state.valueset : undefined,
        fetch: (name: string) => dispatch({ type: 'fetch', name })
    };

    return value;
};
