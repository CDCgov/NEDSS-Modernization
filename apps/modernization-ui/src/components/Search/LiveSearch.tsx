import { Icon } from '@trussworks/react-uswds';
import { useId } from 'react';

import classNames from 'classnames';
import styles from './search.module.scss';
import { TextInput } from 'design-system/input/text';

type SearchProps = {
    id?: string;
    name: string;
    className?: string;
    placeholder?: string;
    ariaLabel?: string;
    value: string;
    onChange: (keyword: string) => void;
};

const LiveSearch = (props: SearchProps) => {
    const defaultId = useId();

    return (
        <search className={classNames(styles.livesearch, props.className)}>
            <label className="usa-sr-only" htmlFor={props.id}>
                Search
            </label>
            <Icon.Search size={3} name="Search" aria-label='search'/>
            <TextInput
                placeholder={props.placeholder ?? 'Search...'}
                type="search"
                id={props.id ?? defaultId}
                name={props.name}
                aria-label={props.ariaLabel}
                value={props.value}
                onChange={(value) => props.onChange(value ?? '')}
                maxLength={50}
                clearable={true}
            />
        </search>
    );
};

export { LiveSearch };
