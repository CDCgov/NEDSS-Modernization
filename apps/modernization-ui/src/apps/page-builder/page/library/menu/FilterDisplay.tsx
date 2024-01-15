import { Filter } from 'filters';
import styles from './filter-display.module.scss';
import { asString } from 'filters/asDisplayableFilter';

type Props = {
    filters: Filter[];
};
export const FilterDisplay = ({ filters }: Props) => {
    const renderProperty = (filter: Filter): string => {
        return asString(filter);
    };
    return (
        <div className={styles.filterDisplay}>
            {filters.length > 0 && (
                <div className={styles.filter}>
                    <span className={styles.filterText}>{renderProperty(filters[0])}</span>
                    {filters.length > 0 && (
                        <span className={styles.moreText}>&nbsp;{`and ${filters.length - 1} others`}</span>
                    )}
                </div>
            )}
        </div>
    );
};
