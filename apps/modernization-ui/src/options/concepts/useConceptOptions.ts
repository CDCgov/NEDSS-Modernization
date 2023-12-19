import { useEffect, useReducer } from 'react';
import { ConceptOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { authorization } from 'authorization';

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

type ConceptOptions = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const useConceptOptions = (valueSet: string, { lazy = true }: Settings): ConceptOptions => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (!lazy) {
            dispatch({ type: 'load' });
        }
    }, []);

    useEffect(() => {
        if (state.status === 'loading') {
            ConceptOptionsService.allUsingGet({
                authorization: authorization(),
                name: valueSet
            }).then((response) => dispatch({ type: 'loaded', options: response.options }));
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
