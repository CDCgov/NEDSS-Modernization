import { useState, KeyboardEvent as ReactKeyboardEvent } from 'react';
import { Button, Icon, TextInput } from '@trussworks/react-uswds';

import styles from './search.module.scss';
import classNames from 'classnames';

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

    return (
        <search className={classNames(styles.search, props.className)}>
            <label className="usa-sr-only" htmlFor={props.id}>
                Search
            </label>
            <TextInput
                placeholder={props.placeholder}
                type="search"
                id={props.id}
                name={props.name}
                aria-label={props.ariaLabel}
                defaultValue={props.value}
                onChange={(event) => setKeyword(event.target.value)}
                onKeyDown={handleEnter}
            />
            <Button type="button" onClick={handleSearch}>
                <Icon.Search size={3} name="Search" />
            </Button>
        </search>
    );
};

export { Search };
