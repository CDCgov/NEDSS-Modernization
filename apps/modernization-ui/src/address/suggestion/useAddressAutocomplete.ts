import { LocationCodedValues, StateCodedValue } from 'location';
import { useConfiguration } from 'configuration';
import { useAddressAutocompleteAPI } from './useAddressAutocompleteAPI';

type AddressSuggestion = {
    address1: string;
    city: string;
    state: StateCodedValue | null;
    zip: string;
};

type SuggestionCriteria = {
    search: string;
    city?: string;
    state?: string;
    zip?: string;
    maxResults?: number;
};

type Props = {
    locations: LocationCodedValues;
};

const noop = () => {
    const suggest = () => {};
    const reset = () => {};

    return { suggestions: [], suggest, reset };
};

const useAddressAutocomplete = ({ locations }: Props) => {
    const configuration = useConfiguration();

    const enabled = configuration.features.address.autocomplete;

    const key = configuration.settings.smarty?.key;

    return enabled && key ? useAddressAutocompleteAPI({ key, locations }) : noop();
};

export { useAddressAutocomplete };
export type { AddressSuggestion, SuggestionCriteria };
