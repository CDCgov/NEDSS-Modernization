import { PageRuleControllerService, Rule } from 'apps/page-builder/generated';
import { useEffect, useReducer } from 'react';
import { useParams } from 'react-router';

type State =
    | { status: 'idle' }
    | { status: 'fetching' }
    | { status: 'complete'; rules: void | Rule[] }
    | { status: 'error'; error: string };

type Action = { type: 'fetch' } | { type: 'complete'; rules: void | Rule[] } | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching' };
        case 'complete':
            return { status: 'complete', rules: action.rules };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useGetAllPageRules = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    const { pageId } = useParams();

    useEffect(() => {
        if (state.status === 'fetching' && pageId) {
            PageRuleControllerService.getAllRules({
                id: Number(pageId)
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', rules: response }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        rules: state.status === 'complete' ? state.rules : undefined,
        fetch: () => dispatch({ type: 'fetch' })
    };
    return value;
};
