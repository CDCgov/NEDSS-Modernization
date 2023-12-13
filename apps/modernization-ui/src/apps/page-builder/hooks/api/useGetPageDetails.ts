import { PagesResponse, PagesService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; page: number }
    | { status: 'complete'; details: PagesResponse }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; page: number }
    | { type: 'complete'; details: PagesResponse }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', page: action.page };
        case 'complete':
            return { status: 'complete', details: action.details };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useGetPageDetails = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'fetching') {
            PagesService.detailsUsingGet({
                authorization: authorization(),
                id: state.page
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => {
                    if (response) {
                        dispatch({ type: 'complete', details: response });
                    } else {
                        dispatch({ type: 'error', error: 'Failed to retrieve page details' });
                    }
                });
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'fetching',
        pageDetails: state.status === 'complete' ? state.details : undefined,
        fetch: (page: number) => dispatch({ type: 'fetch', page })
    };

    return value;
};
