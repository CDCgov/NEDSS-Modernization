import { ChangeEvent as ReactChangeEvent } from 'react';
import classNames from 'classnames';

import styles from './search-result-page-size-select.module.scss';

type SearchResultPageSizeSelectProps = {
    id: string;
    value?: number;
    selections: number[];
    onPageSizeChanged: (size: number) => void;
};

const SearchResultPageSizeSelect = ({
    id,
    selections = [],
    value,
    onPageSizeChanged
}: SearchResultPageSizeSelectProps) => {
    const current = value ?? Math.min(...selections);

    const handleChange = (event: ReactChangeEvent<HTMLSelectElement>) => {
        const selected = Number(event.target.value);
        onPageSizeChanged(selected);
    };

    return (
        <div className={styles.selector}>
            <label htmlFor={id}>Results per page</label>
            <select key={current} className={classNames('usa-select')} id={id} value={current} onChange={handleChange}>
                {selections.map((selection, index) => (
                    <option key={index} value={selection}>
                        {selection}
                    </option>
                ))}
            </select>
        </div>
    );
};

export { SearchResultPageSizeSelect };
