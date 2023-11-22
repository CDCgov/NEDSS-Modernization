import { useEffect, useReducer } from 'react';
import { Option } from 'generated';

type State = { status: 'idle' | 'loading' } | { status: 'loaded'; options: Option[] };

type Action = { type: 'reset' } | { type: 'load' } | { type: 'loaded'; options: Option[] };

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

type PageBuilderOptionsResolver = () => Promise<Option[]>;

type PageBuilderOptions = {
    options: Option[];
    load: () => void;
};

type Parameters = {
    resolver: PageBuilderOptionsResolver;
    lazy?: boolean;
};

const usePageBuilderOptions = ({ resolver, lazy = true }: Parameters): PageBuilderOptions => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, []);

    useEffect(() => {
        if (state.status === 'loading') {
            resolver()
                .then((response) => response ?? [])
                .then((options) => dispatch({ type: 'loaded', options }));
        }
    }, [state.status]);

    const options = state.status === 'loaded' ? state.options : [];
    const load = () => dispatch({ type: 'load' });

    return {
        options,
        load
    };
};

export { usePageBuilderOptions };
export type { PageBuilderOptions };
