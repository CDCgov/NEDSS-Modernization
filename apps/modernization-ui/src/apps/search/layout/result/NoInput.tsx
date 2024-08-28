import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import styles from './no-input.module.scss';

const NoInput = () => {
    return (
        <div className={styles.noResults}>
            <AlertBanner type="warning" iconSize={4}>
                <div className={styles.noResultsContent}>
                    <span className={styles.noResultsHeader}>No results found</span>
                    <span className={styles.noResultsSubHeading}>You must enter at least one item to search.</span>
                </div>
            </AlertBanner>
        </div>
    );
};

export { NoInput };
