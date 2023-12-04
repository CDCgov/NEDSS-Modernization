import { useEffect, useReducer } from 'react';
import { Selectable } from 'options';

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

type PageBuilderOptionsResolver = () => Promise<Selectable[]>;

type PageBuilderOptions = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    resolver: PageBuilderOptionsResolver;
    lazy?: boolean;
};

const usePageBuilderOptions = ({ resolver, lazy = true }: Settings): PageBuilderOptions => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, [lazy]);

    useEffect(() => {
        if (state.status === 'loading') {
            resolver()
                .then((response) => response ?? [])
                .then((options) => dispatch({ type: 'loaded', options }));
        }
    }, [resolver, state.status]);

    const options = state.status === 'loaded' ? state.options : [];
    const load = () => dispatch({ type: 'load' });

    return {
        options,
        load
    };
};

export { usePageBuilderOptions };
export type { PageBuilderOptions };
