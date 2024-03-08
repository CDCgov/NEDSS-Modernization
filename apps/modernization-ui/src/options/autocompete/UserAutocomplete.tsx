import { KeyboardEvent as ReactKeyboardEvent, useRef, useState, useEffect, ReactNode } from 'react';
import { Suggestions } from 'suggestion/Suggestions';
import { Selectable } from 'options/selectable';
import { useUserOptionsAutocomplete } from 'options/users/useUserOptionsAutocomplete';
import { TextInput } from '@trussworks/react-uswds';
import { EntryWrapper } from 'components/Entry';

type Props = {
    id: string;
    label: string;
    className?: string;
    placeholder?: string;
    value?: Selectable;
    defaultValue?: any;
    onChange?: (value?: string) => void;
};

const UserAutocomplete = ({ id, label, placeholder, value, defaultValue, onChange }: Props) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);
    const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
        return <>{suggestion.label}</>;
    };

    const [entered, setEntered] = useState(defaultValue || value?.name);

    const { options, suggest, reset } = useUserOptionsAutocomplete({ initialCriteria: entered });

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

    useEffect(() => {
        reset(value?.name);
    }, []);

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
            onChange(option.value);
        }
    };

    const handleCancel = () => {
        reset();
        inputRef?.current?.focus();
    };

    return (
        <div>
            {defaultValue}
            {entered}
            <EntryWrapper orientation={'vertical'} label={label ?? ''} htmlFor={id ?? ''}>
                <TextInput
                    inputRef={inputRef}
                    className="usa-input"
                    id={id}
                    data-testid={id}
                    inputMode="text"
                    placeholder={placeholder}
                    type="text"
                    autoComplete="off"
                    value={entered || ''}
                    name={id ?? ''}
                    onChange={(event) => setEntered(event.target.value)}
                    onKeyDown={handleKeyDown}
                />

                <Suggestions
                    listRef={suggestionRef}
                    id={`${id}-options-autocomplete`}
                    suggestions={options}
                    renderSuggestion={renderSuggestion}
                    onSelection={(event) => handleSelection(event)}
                    onCancel={handleCancel}
                />
            </EntryWrapper>
        </div>
    );
};

export { UserAutocomplete };
