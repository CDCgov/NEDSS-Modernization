import styles from './no-input.module.scss';
import { AlertMessage } from 'design-system/message';

const NoInput = () => {
    return (
        <div className={styles.noResults}>
            <AlertMessage
                type="warning"
                tabIndex={0}
                role="alert"
                aria-label="No results found, you must enter at least one item to search.">
                <div className={styles.noResultsContent}>
                    <span className={styles.noResultsHeader}>No results found</span>
                    <span className={styles.noResultsSubHeading}>You must enter at least one item to search.</span>
                </div>
            </AlertMessage>
        </div>
    );
};

export { NoInput };
