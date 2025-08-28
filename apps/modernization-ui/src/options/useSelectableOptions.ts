import { useCallback, useEffect, useReducer } from 'react';
import { SelectableResolver, findByValue } from './findByValue';
import { Selectable } from './selectable';

type State<C> = { status: 'idle' | 'loading'; criteria?: C } | { status: 'loaded'; options: Selectable[] };

type Action<C> = { type: 'reset' } | { type: 'load'; criteria?: C } | { type: 'loaded'; options: Selectable[] };

const reducer = <C>(_state: State<C>, action: Action<C>): State<C> => {
    switch (action.type) {
        case 'load':
            return { status: 'loading', criteria: action.criteria };
        case 'loaded':
            return { status: 'loaded', options: action.options };
        default:
            return { status: 'idle' };
    }
};

type SelectableOptionsInteraction<C> = {
    load: (criteria?: C) => void;
    options: Selectable[];
    resolve: SelectableResolver;
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
            resolver(state.criteria).then((resolved) => dispatch({ type: 'loaded', options: resolved }));
        }
    }, [state.status]);

    const options = state.status === 'loaded' ? state.options : [];
    const load = useCallback((criteria?: V) => dispatch({ type: 'load', criteria }), [dispatch]);
    const resolve = findByValue(options);

    return {
        options,
        load,
        resolve
    };
};

export { useSelectableOptions };
export type { SelectableOptionsInteraction };
