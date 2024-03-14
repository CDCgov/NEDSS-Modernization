import { useEffect, useReducer } from 'react';

import { Condition, ConditionControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; page?: number }
    | { status: 'complete'; conditions: Condition[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; page?: number }
    | { type: 'complete'; conditions: Condition[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', page: action.page };
        case 'complete':
            return { status: 'complete', conditions: action.conditions };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useFindConditionsNotInUse = (page?: number) => {
    const [state, dispatch] = useReducer(reducer, { status: 'fetching', page: page });

    useEffect(() => {
        if (state.status === 'fetching') {
            ConditionControllerService.findConditionsNotInUseUsingGet({
                authorization: authorization(),
                page: page ?? -1
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) =>
                    response
                        ? dispatch({ type: 'complete', conditions: response })
                        : dispatch({ type: 'error', error: 'Failed to retrieve question' })
                );
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        conditions: state.status === 'complete' ? state.conditions : [],
        fetch: (page: number) => dispatch({ type: 'fetch', page })
    };

    return value;
};
