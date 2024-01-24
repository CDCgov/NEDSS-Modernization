import { Button, Icon, TextInput } from '@trussworks/react-uswds';
import { KeyboardEvent as ReactKeyboardEvent, useEffect, useState } from 'react';

import classNames from 'classnames';
import styles from './search.module.scss';

type SearchProps = {
    id: string;
    name: string;
    className?: string;
    placeholder?: string;
    value?: string;
    ariaLabel?: string;
    onSearch?: (keyword?: string) => void;
};

const Search = (props: SearchProps) => {
    const [keyword, setKeyword] = useState<string>();
    const [resetInput, setResetInput] = useState<boolean>(false);
    const handleSearch = () => {
        if (props.onSearch) {
            props.onSearch(keyword);
        }
    };

    const handleEnter = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key == 'Enter') {
            handleSearch();
        }
    };

    useEffect(() => {
        setResetInput(true);
    }, [props.value]);

    useEffect(() => {
        if (resetInput) {
            setResetInput(false);
        }
    }, [resetInput]);
    return (
        <search className={classNames(styles.search, props.className)}>
            <label className="usa-sr-only" htmlFor={props.id}>
                Search
            </label>
            {!resetInput && (
                <TextInput
                    placeholder={props.placeholder}
                    type="search"
                    id={props.id}
                    name={props.name}
                    aria-label={props.ariaLabel}
                    defaultValue={props.value}
                    onChange={(event) => setKeyword(event.target.value)}
                    onKeyDown={handleEnter}
                    maxLength={50}
                />
            )}
            <Button type="button" onClick={handleSearch}>
                <Icon.Search size={3} name="Search" />
            </Button>
        </search>
    );
};

export { Search };
