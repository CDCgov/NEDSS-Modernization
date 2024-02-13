import { KeyboardEvent as ReactKeyboardEvent, useRef, useState, useEffect, ReactNode } from 'react';
import { Suggestions } from 'suggestion/Suggestions';
import { Selectable } from 'options/selectable';
import { useFacilityOptionsAutocomplete } from 'options/facilities/useFacilityOptionsAutocomplete';
import { Label } from '@trussworks/react-uswds';

type Props = {
    id: string;
    label: string;
    className?: string;
    placeholder?: string;
    value?: Selectable;
    onChange?: (value?: string) => void;
};

const FacilityAutocomplete = ({ id, label, placeholder, value, onChange }: Props) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);
    const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
        return <>{suggestion.label}</>;
    };

    const [entered, setEntered] = useState(value?.name || '');

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
            onChange(option.value);
        }
    };

    const handleCancel = () => {
        reset();
        inputRef?.current?.focus();
    };

    return (
        <div>
            <Label className="usa-label" htmlFor={id}>
                {label}
            </Label>

            <input
                ref={inputRef}
                className="usa-input"
                id={id}
                inputMode="text"
                placeholder={placeholder}
                type="text"
                autoComplete="off"
                value={entered}
                name={id}
                onChange={(event) => setEntered(event.target.value)}
                onKeyDown={handleKeyDown}
            />
            <Suggestions
                listRef={suggestionRef}
                id={`${id}-options-autocomplete`}
                suggestions={options}
                renderSuggestion={renderSuggestion}
                onSelection={handleSelection}
                onCancel={handleCancel}
            />
        </div>
    );
};

export { FacilityAutocomplete };
