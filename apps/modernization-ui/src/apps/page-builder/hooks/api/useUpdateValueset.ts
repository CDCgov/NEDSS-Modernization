import { useEffect, useReducer } from 'react';

import { ValueSetControllerService, Valueset } from 'apps/page-builder/generated';
import { UpdateValueSetRequest } from 'apps/page-builder/generated/models/UpdateValueSetRequest';
import { authorization } from 'authorization';

type State =
    | { status: 'idle' }
    | { status: 'updating'; valueset: string; request: UpdateValueSetRequest }
    | { status: 'complete'; valueset: Valueset }
    | { status: 'error'; error: string };

type Action =
    | { type: 'update'; valueset: string; request: UpdateValueSetRequest }
    | { type: 'complete'; valueset: Valueset }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'update':
            return { status: 'updating', valueset: action.valueset, request: action.request };
        case 'complete':
            return { status: 'complete', valueset: action.valueset };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useUpdateValueset = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'updating') {
            ValueSetControllerService.updateValueSetUsingPut({
                authorization: authorization(),
                codeSetNm: state.valueset,
                request: state.request
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', valueset: response }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'updating',
        response: state.status === 'complete' ? state.valueset : undefined,
        update: (valueset: string, request: UpdateValueSetRequest) => dispatch({ type: 'update', valueset, request })
    };

    return value;
};
