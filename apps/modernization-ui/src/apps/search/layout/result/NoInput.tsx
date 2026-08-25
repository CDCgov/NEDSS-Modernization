import { AlertMessage } from 'design-system/message';

import styles from './no-input.module.scss';

const NoInput = () => {
    return (
        <div className={styles.noResults}>
            <AlertMessage type="warning" tabIndex={0} title="No results found">
                You must enter at least one item to search.
            </AlertMessage>
        </div>
    );
};

export { NoInput };
