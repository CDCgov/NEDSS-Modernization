import { Input } from 'components/FormInputs/Input';
import { Suggestions } from 'suggestion/Suggestions';
import { AddressSuggestion, useAddressAutocomplete } from './useAddressAutocomplete';
import { ReactElement, KeyboardEvent, ChangeEvent, useRef, useState, useEffect } from 'react';
import { LocationCodedValues } from 'location';

const renderSuggestion = (suggestion: AddressSuggestion) => (
    <>
        {suggestion.address1}
        <br />
        {suggestion.city}, {suggestion.state?.abbreviation} {suggestion.zip}
    </>
);

type Criteria = {
    zip?: string;
    city?: string;
    state?: string;
};

type Props = {
    id: string;
    locations: LocationCodedValues;
    criteria?: Criteria;
    label?: string;
    className?: string;
    placeholder?: string;
    defaultValue?: string;
    flexBox?: boolean;
    error?: string;
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
    onSelection?: (suggestion: AddressSuggestion) => void;
};

const AddressSuggestionInput = (props: Props): ReactElement => {
    const suggestionRef = useRef<HTMLUListElement>(null);

    const { suggestions, suggest, reset } = useAddressAutocomplete({ locations: props.locations });

    const [adjustedCriteria, setAdjustedCriteria] = useState<Criteria>();

    useEffect(() => {
        const state = props.criteria?.state && props.locations.states.byValue(props.criteria?.state)?.abbreviation;

        setAdjustedCriteria({
            zip: props.criteria?.zip,
            city: props.criteria?.city,
            state
        });
    }, [props.criteria?.city, props.criteria?.zip, props.criteria?.state]);

    const handleKeyDown = (event: KeyboardEvent) => {
        if (event.key === 'ArrowDown') {
            event.preventDefault();
            suggestionRef.current?.focus();
        } else if (event.key === 'Escape') {
            event.preventDefault();
            reset();
        }
    };

    const handleOnChange = (event: ChangeEvent<HTMLInputElement>) => {
        const search = event.target.value;

        suggest({ search, ...adjustedCriteria });

        if (props.onChange) {
            props.onChange(event);
        }
    };

    const handleSelection = (suggestion: AddressSuggestion) => {
        reset({ search: suggestion.address1 });

        if (props.onSelection) {
            props.onSelection(suggestion);
        }
    };

    const handleCancel = () => {
        reset();
        //  ideally a ref would be used
        document.getElementById(props.id)?.focus();
    };

    return (
        <>
            <Input
                id={props.id}
                label={props.label}
                htmlFor={props.label}
                type="text"
                className={props.className}
                defaultValue={props.defaultValue}
                placeholder={props.placeholder}
                autoComplete="off"
                flexBox={props.flexBox}
                error={props.error}
                onChange={handleOnChange}
                onKeyDown={handleKeyDown}
            />
            <Suggestions
                listRef={suggestionRef}
                id={`${props.id}-address-suggestions`}
                suggestions={suggestions}
                renderSuggestion={renderSuggestion}
                onSelection={handleSelection}
                onCancel={handleCancel}
            />
        </>
    );
};

export { AddressSuggestionInput };
