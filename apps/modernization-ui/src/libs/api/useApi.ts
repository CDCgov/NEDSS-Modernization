import { useCallback, useEffect, useMemo, useReducer } from 'react';

type State<I, O> =
    | { status: 'idle' }
    | { status: 'fetching'; input: I }
    | { status: 'complete'; data: O }
    | { status: 'error'; error: string };

type Action<I, O> = { type: 'fetch'; input: I } | { type: 'complete'; data: O } | { type: 'error'; error: string };

const reducer = <I, O>(_current: State<I, O>, action: Action<I, O>): State<I, O> => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', input: action.input };
        case 'complete':
            return { status: 'complete', data: action.data };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

type ApiInteraction<I, O> = {
    error?: string;
    isLoading: boolean;
    data?: O;
    load: (input: I) => void;
};

type ApiProps<I, O> = {
    resolver: (input: I) => Promise<O>;
};

const useApi = <I, O>({ resolver }: ApiProps<I, O>): ApiInteraction<I, O> => {
    const [state, dispatch] = useReducer(reducer<I, O>, { status: 'idle' });

    useEffect(() => {
        if (state.status === 'fetching') {
            resolver(state.input).then(
                (result) => dispatch({ type: 'complete', data: result }),
                () => dispatch({ type: 'error', error: 'An unexpected error occurred.' })
            );
        }
    }, [state.status]);

    const load = useCallback((input: I) => dispatch({ type: 'fetch', input }), [dispatch]);

    const interaction = useMemo(
        () => ({
            error: state.status === 'error' ? state.error : undefined,
            isLoading: state.status === 'fetching',
            data: state.status === 'complete' ? state.data : undefined,
            load
        }),
        [state, load]
    );

    return interaction;
};

export { useApi };
export type { ApiInteraction };
