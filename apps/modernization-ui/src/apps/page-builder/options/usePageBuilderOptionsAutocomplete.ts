import { useEffect, useReducer } from 'react';
import debounce from 'lodash.debounce';
import { Selectable } from 'options';

type State =
    | { status: 'idle'; criteria?: string }
    | { status: 'suggested'; criteria?: string; options: Selectable[] }
    | { status: 'suggesting'; criteria: string; limit: number };

type Action =
    | { type: 'reset'; criteria?: string }
    | { type: 'suggest'; criteria: string; limit: number }
    | { type: 'suggested'; suggestions: Selectable[] };

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

type SuggestRequester = (criteria: string, limit?: number) => void;

type AutocompleteOptionsResolver = (criteria: string, limit?: number) => Promise<Selectable[]>;

type PageBuilderOptionsAutocompletion = {
    criteria?: string;
    options: Selectable[];
    suggest: SuggestRequester;
    reset: (criteria?: string) => void;
    complete: AutocompleteOptionsResolver;
};

type PageBuilderOptionsAutocompleteParameters = {
    resolver: AutocompleteOptionsResolver;
    criteria?: string;
    limit?: number;
};

const usePageBuilderOptionsAutocomplete = ({
    resolver,
    criteria = '',
    limit
}: PageBuilderOptionsAutocompleteParameters): PageBuilderOptionsAutocompletion => {
    const [state, dispatch] = useReducer(reducer, criteria, initial);

    useEffect(() => {
        if (state.status === 'suggesting') {
            resolver(criteria, limit)
                .then((response) => response ?? [])
                .then((options) => dispatch({ type: 'suggested', suggestions: options }));
        }
    }, [criteria, limit, resolver, state.status]);

    const options = state.status === 'suggested' ? state.options : [];
    const suggest = (criteria = '', limit = 15) => dispatch({ type: 'suggest', criteria, limit });
    const reset = (criteria?: string) => dispatch({ type: 'reset', criteria });

    return {
        criteria: state.criteria,
        options,
        suggest: debounce(suggest, 600),
        reset,
        complete: resolver
    };
};

export { usePageBuilderOptionsAutocomplete };
export type { PageBuilderOptionsAutocompletion, AutocompleteOptionsResolver };
