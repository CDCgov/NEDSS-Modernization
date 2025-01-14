import { KeyboardEvent as ReactKeyboardEvent, useRef, useState, useEffect, ReactNode } from 'react';
import { TextInput } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable } from 'options/selectable';
import { AutocompleteOptionsResolver, useSelectableAutocomplete } from 'options/autocompete';
import { Suggestions } from 'suggestion/Suggestions';

type SuggestionRenderer = (suggestion: Selectable) => ReactNode;
type TextConverter<V> = (value?: V) => string | undefined;
type ValueConverter<V> = (suggestion?: Selectable) => V | undefined;

const renderSuggestion = (suggestion: Selectable): ReactNode => suggestion.name;

type AutocompleteSingleProps<V> = {
    id: string;
    label: string;
    value?: V;
    onChange?: (value?: V) => void;
    onEntered?: (value: string) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    asValue?: ValueConverter<V>;
    asText?: TextConverter<V>;
    asSuggestion?: SuggestionRenderer;
    onBlur?: any;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'onBlur' | 'value'>;

const Autocomplete = <V,>({
    id,
    label,
    placeholder,
    value,
    onChange,
    onEntered,
    orientation,
    sizing,
    error,
    required,
    onBlur,
    asValue,
    asText,
    asSuggestion = renderSuggestion,
    resolver
}: AutocompleteSingleProps<V> & { resolver: AutocompleteOptionsResolver }) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);

    // setting to empty string prevents error: A component is changing an uncontrolled input to be controlled
    const [entered, setEntered] = useState(asText?.(value) ?? '');

    const { options, suggest, reset } = useSelectableAutocomplete({ resolver, criteria: entered });

    useEffect(() => {
        reset(asText?.(value));
    }, []);

    useEffect(() => {
        if (entered) {
            suggest(entered);
        } else {
            reset();
        }
    }, [entered]);

    const handleKeyDown = (event: ReactKeyboardEvent) => {
        if (event.key === 'ArrowDown') {
            event.preventDefault();
            suggestionRef.current?.focus();
        } else if (event.key === 'Escape') {
            event.preventDefault();
            reset();
        }
    };

    const handleSelection = (option: Selectable) => {
        reset(option.name);
        setEntered(option.name ?? '');
        if (onChange) {
            onChange(asValue?.(option));
            onBlur?.();
        }
    };

    useEffect(() => {
        !value && setEntered('');
    }, [value]);

    const handleCancel = () => {
        reset();
        inputRef?.current?.focus();
    };

    return (
        <div className={classNames('input', { 'input--error': error })}>
            <EntryWrapper
                orientation={orientation}
                sizing={sizing}
                label={label}
                htmlFor={id}
                required={required}
                error={error}>
                <TextInput
                    inputRef={inputRef}
                    className="usa-input"
                    id={id}
                    data-testid={id}
                    validationStatus={error ? 'error' : undefined}
                    aria-describedby={error ? `${error}-message` : undefined}
                    inputMode="text"
                    placeholder={placeholder}
                    type="text"
                    autoComplete="off"
                    value={entered}
                    name={id}
                    onChange={(event) => {
                        setEntered(event.target.value);
                        onEntered?.(event.target.value);
                    }}
                    onBlur={onBlur}
                    onKeyDown={handleKeyDown}
                    required={required}
                />

                <Suggestions
                    listRef={suggestionRef}
                    id={`${id}-options-autocomplete`}
                    suggestions={options}
                    renderSuggestion={asSuggestion}
                    onSelection={handleSelection}
                    onCancel={handleCancel}
                />
            </EntryWrapper>
        </div>
    );
};

export { Autocomplete };

export type { AutocompleteSingleProps };
