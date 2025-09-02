import { useCallback, useEffect, useReducer } from 'react';
import { Selectable } from './selectable';

type State<C> =
    | { status: 'idle' | 'loading'; criteria?: C }
    | { status: 'loaded'; criteria?: C; options: Selectable[] };

type Action<C> =
    | { type: 'reset' }
    | { type: 'load'; criteria?: C }
    | { type: 'loaded'; criteria?: C; options: Selectable[] };

const reducer = <C>(current: State<C>, action: Action<C>): State<C> => {
    switch (action.type) {
        case 'load':
            return { status: 'loading', criteria: action.criteria };
        case 'loaded':
            if (current.status === 'loading' && current.criteria === action.criteria) {
                // the options for the current criteria have been loaded, update the state
                return { status: 'loaded', options: action.options, criteria: action.criteria };
            } else {
                // ignore loaded action for previous criteria
                return current;
            }
        default:
            return { status: 'idle' };
    }
};

type SelectableOptionsInteraction<C> = {
    load: (criteria?: C) => void;
    options: Selectable[];
};

type SelectableOptionsSettings<C> = {
    resolver: (criteria?: C) => Promise<Selectable[]>;
    lazy?: boolean;
};

const useSelectableOptions = <V = undefined>({
    resolver,
    lazy = false
}: SelectableOptionsSettings<V>): SelectableOptionsInteraction<V> => {
    const [state, dispatch] = useReducer(reducer<V>, {
        status: 'idle'
    });

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, []);

    useEffect(() => {
        if (state.status === 'loading') {
            resolver(state.criteria).then((resolved) =>
                dispatch({ type: 'loaded', criteria: state.criteria, options: resolved })
            );
        }
    }, [state.status, state.criteria]);

    const options = state.status === 'loaded' ? state.options : [];
    const load = useCallback((criteria?: V) => dispatch({ type: 'load', criteria }), [dispatch]);

    return {
        options,
        load
    };
};

export { useSelectableOptions };
export type { SelectableOptionsInteraction };
