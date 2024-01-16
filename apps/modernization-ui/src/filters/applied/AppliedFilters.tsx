import { Icon } from '@trussworks/react-uswds';
import { Filter } from 'filters';

import { asString } from 'filters/asDisplayableFilter';
import styles from './applied-filters.module.scss';

type AppliedFiltersProps = {
    label: string;
    filters: Filter[];
    onRemove: (filter: string) => void;
};

const AppliedFilters = ({ label, filters, onRemove }: AppliedFiltersProps) => {
    return (
        <div className={styles.applied}>
            Show the Following
            <p className={styles.title}>All {label}</p>
            {filters.length > 0 && <AppliedFilterList filters={filters} onRemove={onRemove} />}
        </div>
    );
};

type AppliedFilterListProps = {
    filters: Filter[];
    onRemove: (filter: string) => void;
};

const AppliedFilterList = ({ filters, onRemove }: AppliedFilterListProps) => {
    return (
        <>
            <label>Matching all of these filters</label>
            <ul>
                {filters.map((filter, index) => (
                    <AppliedFilter key={index} filter={filter} onRemove={onRemove} />
                ))}
            </ul>
        </>
    );
};

type AppliedFilterProps = {
    filter: Filter;
    onRemove: (filter: string) => void;
};

const AppliedFilter = ({ filter, onRemove }: AppliedFilterProps) => (
    <li>
        <span>{asString(filter)}</span>
        <Icon.Close role="button" aria-label="close" onClick={() => onRemove(filter.id)} />
    </li>
);

export { AppliedFilters };
