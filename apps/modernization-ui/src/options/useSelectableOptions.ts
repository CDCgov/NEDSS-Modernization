import { useEffect, useReducer } from 'react';
import { SelectableResolver, findByValue } from './findByValue';
import { Selectable } from './selectable';

type State = { status: 'idle' | 'loading' } | { status: 'loaded'; options: Selectable[] };

type Action = { type: 'reset' } | { type: 'load' } | { type: 'loaded'; options: Selectable[] };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'load':
            return { ...initial, status: 'loading' };
        case 'loaded':
            return { status: 'loaded', options: action.options };
        default:
            return { ...initial };
    }
};

const initial: State = {
    status: 'idle'
};

type Interaction = {
    load: () => void;
    options: Selectable[];
    resolve: SelectableResolver;
};

type Settings = {
    resolver: () => Promise<Selectable[]>;
    lazy?: boolean;
};

const useSelectableOptions = ({ resolver, lazy = false }: Settings): Interaction => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, []);

    useEffect(() => {
        if (state.status === 'loading') {
            resolver().then((resolved) => dispatch({ type: 'loaded', options: resolved }));
        }
    }, [state.status]);

    const options = state.status === 'loaded' ? state.options : [];
    const load = () => dispatch({ type: 'load' });
    const resolve = findByValue(options);

    return {
        options,
        load,
        resolve
    };
};

export { useSelectableOptions };
