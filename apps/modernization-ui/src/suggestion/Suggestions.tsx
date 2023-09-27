import classNames from 'classnames';
import './Suggestions.scss';
import React, { ReactNode, RefObject, useEffect, useState } from 'react';

type Props<T> = {
    listRef?: RefObject<HTMLUListElement>;
    id?: string;
    suggestions: T[];
    renderSuggestion?: (suggestion: T) => ReactNode;
    onSelection?: (suggestion: T) => void;
    onCancel?: () => void;
};

const defaultRenderer = <T,>(suggestion: T) => <>{JSON.stringify(suggestion)}</>;

const Suggestions = <T,>({
    listRef,
    id = 'suggestions',
    suggestions,
    renderSuggestion = defaultRenderer,
    onSelection = () => {},
    onCancel = () => {}
}: Props<T>) => {
    const [isShown, shown] = useState(false);

    const [active, setActive] = useState(-1);

    useEffect(() => {
        shown(suggestions && suggestions.length > 0);
    }, [suggestions]);

    const handleSelection = (suggestion: T) => {
        shown(false);
        onSelection(suggestion);
    };

    const handleClick = (suggestion: T) => {
        handleSelection(suggestion);
    };

    const handleCancel = () => {
        shown(false);
        onCancel();
    };

    const handleOutsideClick = () => {
        shown(false);
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLUListElement>) => {
        event.preventDefault();
        if (event.key === 'ArrowDown') {
            setActive((existing) => existing + 1);
        } else if (event.key === 'ArrowUp') {
            setActive((existing) => existing - 1);
        } else if (event.key === 'Enter') {
            handleSelection(suggestions[active]);
        } else if (event.key === 'Escape') {
            handleCancel();
        }
    };

    useEffect(() => {
        // Bind the event listener
        document.addEventListener('click', handleOutsideClick);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener('click', handleOutsideClick);
        };
    }, []);

    return (
        <ul
            ref={listRef}
            id={id}
            data-testid="suggestions-list"
            className="suggestions use-combo-box__list"
            tabIndex={-1}
            role="listbox"
            hidden={!isShown}
            onFocus={() => setActive(0)}
            onKeyDown={handleKeyDown}
            onMouseLeave={() => setActive(-1)}>
            {suggestions.map((suggestion, idx) => (
                <li
                    key={idx}
                    role="option"
                    className={classNames('usa-combo-box__list-option', {
                        'usa-combo-box__list-option--focused': idx == active
                    })}
                    aria-posinset={idx + 1}
                    onClick={() => handleClick(suggestion)}
                    onMouseEnter={() => setActive(idx)}>
                    {renderSuggestion(suggestion)}
                </li>
            ))}
        </ul>
    );
};

export { Suggestions };
