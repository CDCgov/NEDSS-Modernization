import { useEffect, useReducer, useCallback } from 'react';
import debounce from 'lodash.debounce';
import { LocationCodedValues } from 'location';
import { AddressSuggestion, SuggestionCriteria } from './useAddressAutocomplete';

type Result = {
    street_line: string;
    secondary: string;
    city: string;
    state: string;
    zipcode: string;
    entries: number;
};

type Props = {
    key: string;
    locations: LocationCodedValues;
};

enum Status {
    Idle,
    Suggesting,
    Suggested
}

type State = {
    status: Status;
    criteria: SuggestionCriteria;
    suggestions: AddressSuggestion[];
};

type Action =
    | { type: 'suggest'; criteria: SuggestionCriteria }
    | { type: 'suggested'; suggestions: AddressSuggestion[] }
    | { type: 'reset'; criteria?: SuggestionCriteria };

const asAddressSuggestion =
    (locations: LocationCodedValues) =>
    (result: Result): AddressSuggestion => {
        const state = locations.states.byAbbreviation(result.state);

        return {
            address1: result.street_line,
            city: result.city,
            state,
            zip: result.zipcode
        };
    };

const asAddressSuggestions =
    (locations: LocationCodedValues) =>
    (results: Result[]): AddressSuggestion[] =>
        results.map(asAddressSuggestion(locations));

const hasChanged = (existing: SuggestionCriteria, next: SuggestionCriteria) =>
    existing.search !== next.search ||
    existing.zip !== next.zip ||
    existing.city !== next.city ||
    existing.state != next.state;

const reducer = (state: State, action: Action): State => {
    switch (action.type) {
        case 'suggest':
            return hasChanged(state.criteria, action.criteria)
                ? { status: Status.Suggesting, criteria: action.criteria, suggestions: [] }
                : state;
        case 'suggested':
            return { ...state, status: Status.Suggested, suggestions: action.suggestions };
        default:
            return { status: Status.Idle, criteria: action.criteria ?? initial, suggestions: [] };
    }
};

const initial = { search: '' };

const useAddressAutocompleteAPI = ({ key, locations }: Props) => {
    const [state, dispatch] = useReducer(reducer, { status: Status.Idle, criteria: initial, suggestions: [] });

    const suggest = (criteria: SuggestionCriteria) => {
        dispatch({ type: 'suggest', criteria });
    };

    const reset = (criteria?: SuggestionCriteria) => {
        dispatch({ type: 'reset', criteria });
    };

    useEffect(() => {
        if (key && state.status === Status.Suggesting && state.criteria?.search) {
            const parameters = [];

            if (state.criteria.search) {
                parameters.push('search=' + state.criteria.search);
            }

            if (state.criteria.zip) {
                parameters.push('include_only_zip_codes=' + state.criteria.zip);
                //  zip code cannot be used with _cities or _states.
            } else if (state.criteria.city && state.criteria.state) {
                //  filtering by city requires the state also
                parameters.push(`include_only_cities=${state.criteria.city}, ${state.criteria.state}`);
            } else if (state.criteria.state) {
                parameters.push('include_only_states=' + state.criteria.state);
            }

            const query = parameters.join('&');

            fetch(`https://us-autocomplete-pro.api.smarty.com/lookup?key=${key}&${query}`)
                .then((resp) => resp.json())
                .then((data) => data.suggestions ?? [])
                .then(asAddressSuggestions(locations))
                .then((suggestions) => dispatch({ type: 'suggested', suggestions }));
        }
    }, [state.status, state.criteria?.search, state.criteria?.zip, state.criteria?.city, state.criteria?.state]);

    return { suggestions: state.suggestions, suggest: useCallback(debounce(suggest, 600), []), reset };
};

export { useAddressAutocompleteAPI };
