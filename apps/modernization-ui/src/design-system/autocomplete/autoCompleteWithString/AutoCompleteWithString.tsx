import { KeyboardEvent as ReactKeyboardEvent, useRef, useEffect, ReactNode, useState } from 'react';
import { TextInput } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable } from 'options/selectable';
import { AutocompleteOptionsResolver, useSelectableAutocomplete } from 'options/autocompete';
import { Suggestions } from 'suggestion/Suggestions';

const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
    return <>{suggestion.label}</>;
};

type AutoCompleteWithStringProps = {
    id: string;
    label: string;
    value: Selectable | string;
    onChange: (value: Selectable | string) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    asSuggestion?: (suggestion: { label: string; value: string }) => ReactNode;
    onBlur?: () => void;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'onBlur' | 'value'>;

const AutoCompleteWithString = ({
    id,
    label,
    placeholder,
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
    onBlur,
    asSuggestion = renderSuggestion,
    resolver
}: AutoCompleteWithStringProps & { resolver: AutocompleteOptionsResolver }) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);

    const [inputValue, setInputValue] = useState(typeof value === 'string' ? value : value?.value);

    const { options, suggest, reset } = useSelectableAutocomplete({
        resolver,
        criteria: inputValue
    });

    useEffect(() => {
        if (inputValue) {
            suggest(inputValue);
        } else {
            reset();
        }
    }, [inputValue]);

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
        onChange(option);
        onBlur?.();
    };

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
                    value={typeof value === 'string' ? value : value?.name}
                    name={id}
                    onChange={(event) => {
                        setInputValue(event.target.value);
                        typeof event.target.value === 'string' && onChange(event.target.value);
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

export { AutoCompleteWithString };

export type { AutoCompleteWithStringProps };
