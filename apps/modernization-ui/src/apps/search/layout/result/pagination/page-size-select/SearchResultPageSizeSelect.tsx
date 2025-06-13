import { ChangeEvent as ReactChangeEvent } from 'react';
import classNames from 'classnames';
import { usePageSizePreference } from './usePageSizePreference';

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
    const defaultPageSize = Math.min(...selections);
    const { preferencePageSize } = usePageSizePreference(defaultPageSize);
    const current = value ?? preferencePageSize;

    const handleChange = (event: ReactChangeEvent<HTMLSelectElement>) => {
        const selected = Number(event.target.value);
        onPageSizeChanged(selected);
    };

    return (
        <div className={styles.selector}>
            <label htmlFor={id}>Results per page</label>
            <select key={current} className={classNames('usa-select')} id={id} value={current} onChange={handleChange}>
                {selections.map((selection) => (
                    <option key={`page-size-option-${selection}`} value={selection}>
                        {selection}
                    </option>
                ))}
            </select>
        </div>
    );
};

export { SearchResultPageSizeSelect };
