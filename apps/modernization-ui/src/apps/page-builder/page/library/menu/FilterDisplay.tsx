import { Filter } from 'filters';
import { asString } from 'filters/asDisplayableFilter';

import styles from './filter-display.module.scss';

type Props = {
    filters: Filter[];
    onClickFilter: () => void;
};
export const FilterDisplay = ({ filters, onClickFilter }: Props) => {
    const renderProperty = (filter: Filter): string => {
        return asString(filter);
    };
    return (
        <div className={styles.filterDisplay}>
            {filters.length > 0 && (
                <button onClick={onClickFilter} className={styles.clickableFilter}>
                    <span className={styles.filterText}>{renderProperty(filters[0])}</span>
                    {filters.length > 1 && (
                        <span className={styles.moreText}>&nbsp;{`and ${filters.length - 1} others`}</span>
                    )}
                </button>
            )}
        </div>
    );
};
