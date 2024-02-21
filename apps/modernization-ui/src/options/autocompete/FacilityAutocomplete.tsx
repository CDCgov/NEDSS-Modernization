import { KeyboardEvent as ReactKeyboardEvent, useRef, useState, useEffect, ReactNode } from 'react';
import { Suggestions } from 'suggestion/Suggestions';
import { Selectable } from 'options/selectable';
import { useFacilityOptionsAutocomplete } from 'options/facilities/useFacilityOptionsAutocomplete';
import { TextInput } from '@trussworks/react-uswds';
import classNames from 'classnames';
import 'components/FormInputs/Input.scss';
import { EntryWrapper } from 'components/Entry';

type Props = {
    id: string;
    label: string;
    className?: string;
    placeholder?: string;
    value?: Selectable;
    onChange?: (value?: string) => void;
    error?: any;
    required?: any;
    onBlur?: any;
};

const FacilityAutocomplete = ({ id, label, placeholder, value, onChange, error, required, onBlur }: Props) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);
    const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
        return <>{suggestion.label}</>;
    };

    const [entered, setEntered] = useState(value?.name);

    const { options, suggest, reset } = useFacilityOptionsAutocomplete({ initialCriteria: entered });

    useEffect(() => {
        reset(value?.name);
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
            onChange(option.value ?? '');
            onBlur();
        }
    };

    const handleCancel = () => {
        reset();
        inputRef?.current?.focus();
    };

    return (
        <div className={classNames('input', { 'input--error': error })}>
            <EntryWrapper
                orientation={'vertical'}
                label={label ?? ''}
                htmlFor={id ?? ''}
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
                    value={entered ?? ''}
                    name={id ?? ''}
                    onChange={(event) => setEntered(event.target.value)}
                    onBlur={onBlur}
                    onKeyDown={handleKeyDown}
                    required={required}
                />

                <Suggestions
                    listRef={suggestionRef}
                    id={`${id}-options-autocomplete`}
                    suggestions={options}
                    renderSuggestion={renderSuggestion}
                    onSelection={handleSelection}
                    onCancel={handleCancel}
                />
            </EntryWrapper>
        </div>
    );
};

export { FacilityAutocomplete };
