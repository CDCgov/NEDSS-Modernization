import { KeyboardEvent, useRef, useState, useEffect } from 'react';
import { Option } from 'generated';
import { useConceptOptionsAutocomplete } from './useConceptOptionsAutocomplete';
import { Suggestions } from 'suggestion/Suggestions';

const renderSuggestion = (suggestion: Option) => <>{suggestion.name}</>;

type Props = {
    id: string;
    label: string;
    valueSet: string;
    className?: string;
    placeholder?: string;
    value?: Option;
    onChange?: (value?: string) => void;
};

const ConceptAutocomplete = ({ id, label, valueSet, placeholder, value, onChange }: Props) => {
    const suggestionRef = useRef<HTMLUListElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);

    const { options, autocomplete, reset } = useConceptOptionsAutocomplete({ valueSet });

    const [entered, setEntered] = useState(value?.name || '');

    useEffect(() => {
        reset(value?.name);
    }, []);

    useEffect(() => {
        if (entered) {
            autocomplete(entered);
        } else {
            reset();
        }
    }, [entered]);

    const handleKeyDown = (event: KeyboardEvent) => {
        if (event.key === 'ArrowDown') {
            event.preventDefault();
            suggestionRef.current?.focus();
        } else if (event.key === 'Escape') {
            event.preventDefault();
            reset();
        }
    };

    const handleSelection = (option: Option) => {
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
        <>
            <input
                ref={inputRef}
                className="usa-input"
                id={id}
                inputMode="text"
                placeholder={placeholder}
                type="text"
                autoComplete="off"
                value={entered}
                name={label ?? ''}
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
        </>
    );
};

export { ConceptAutocomplete };
