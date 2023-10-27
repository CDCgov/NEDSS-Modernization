import { ConceptOptionsService, Option } from 'generated';
import debounce from 'lodash.debounce';
import { useCallback, useEffect, useReducer } from 'react';
import { useUser } from 'user';

type State =
    | { status: 'idle'; criteria?: string }
    | { status: 'suggested'; criteria?: string; options: Option[] }
    | { status: 'suggesting'; criteria: string; limit: number };

type Action =
    | { type: 'reset'; criteria?: string }
    | { type: 'suggest'; criteria: string; limit: number }
    | { type: 'suggested'; suggestions: Option[] };

const reducer = (state: State, action: Action): State => {
    switch (action.type) {
        case 'suggest':
            //  only suggest if the criteria has changed
            return state.criteria === action.criteria
                ? state
                : { ...initial, status: 'suggesting', criteria: action.criteria, limit: action.limit };
        case 'suggested':
            return { ...state, status: 'suggested', options: action.suggestions };
        case 'reset':
            return { status: 'idle', criteria: action.criteria };
        default:
            return initial();
    }
};

const initial = (criteria?: string): State => ({
    status: 'idle',
    criteria
});

type AutocompleteResolver = (criteria?: string, limit?: number) => void;

type ConceptOptions = {
    criteria?: string;
    options: Option[];
    autocomplete: AutocompleteResolver;
    reset: (criteria?: string) => void;
};

type Options = {
    valueSet: string;
    criteria?: string;
};

const useConceptOptionsAutocomplete = ({ valueSet, criteria = '' }: Options): ConceptOptions => {
    const {
        state: { getToken }
    } = useUser();

    const [state, dispatch] = useReducer(reducer, criteria, initial);

    useEffect(() => {
        if (state.status === 'suggesting') {
            ConceptOptionsService.specificUsingGet({
                authorization: `Bearer ${getToken()}`,
                name: valueSet,
                criteria: state.criteria,
                limit: state.limit
            })
                .then((response) => response.options ?? [])
                .then((options) => dispatch({ type: 'suggested', suggestions: options }));
        }
    }, [state.status]);

    const options = state.status === 'suggested' ? state.options : [];
    const autocomplete = (criteria = '', limit = 15) => dispatch({ type: 'suggest', criteria, limit });
    const reset = (criteria?: string) => dispatch({ type: 'reset', criteria });

    return {
        criteria: state.criteria,
        options,
        autocomplete: useCallback(debounce(autocomplete, 600), []),
        reset
    };
};

export { useConceptOptionsAutocomplete };
