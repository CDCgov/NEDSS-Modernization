import { ConceptOptionsService, Option } from 'generated';
import { useEffect, useReducer } from 'react';
import { useUser } from 'user';

type State = { status: 'idle' | 'loading' } | { status: 'loaded'; options: Option[] };

type Action = { type: 'reset' } | { type: 'load' } | { type: 'loaded'; options: Option[] };

const reducer = (state: State, action: Action): State => {
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

type ConceptOptions = {
    options: Option[];
    load: () => void;
};

type Options = {
    valueSet: string;
    lazy?: boolean;
};

const useConceptOptions = ({ valueSet, lazy = true }: Options): ConceptOptions => {
    const {
        state: { getToken }
    } = useUser();

    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, []);

    useEffect(() => {
        if (state.status === 'loading') {
            ConceptOptionsService.allUsingGet({
                authorization: `Bearer ${getToken()}`,
                name: valueSet
            })
                .then((response) => response.options ?? [])
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

export { useConceptOptions };
