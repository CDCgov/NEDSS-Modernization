import { PagesResponse, PagesService } from 'apps/page-builder/generated';
import { useEffect, useReducer } from 'react';
import { useParams } from 'react-router';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; page: number }
    | { status: 'refreshing'; page: number; details: PagesResponse }
    | { status: 'complete'; details: PagesResponse }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; page: number }
    | { type: 'refresh'; details: PagesResponse }
    | { type: 'complete'; details: PagesResponse }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', page: action.page };
        case 'refresh':
            return { status: 'refreshing', details: action.details, page: action.details.id };
        case 'complete':
            return { status: 'complete', details: action.details };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

type Interaction = {
    loading: boolean;
    page: PagesResponse | undefined;
    fetch: (page: number) => void;
    error?: string;
    refresh: () => void;
};

export const useGetPageDetails = (): Interaction => {
    const [state, dispatch] = useReducer(reducer, initial);
    const { pageId } = useParams();

    useEffect(() => {
        if (pageId) {
            dispatch({ type: 'fetch', page: Number(pageId) });
        }
    }, [pageId, dispatch]);

    useEffect(() => {
        if (state.status === 'fetching' || state.status === 'refreshing') {
            PagesService.details({
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
        loading: state.status === 'fetching' || state.status === 'refreshing',
        page: state.status === 'complete' || state.status === 'refreshing' ? state.details : undefined,
        fetch: (page: number) => dispatch({ type: 'fetch', page }),
        refresh: () => (state.status === 'complete' ? dispatch({ type: 'refresh', details: state.details }) : undefined)
    };

    return value;
};
