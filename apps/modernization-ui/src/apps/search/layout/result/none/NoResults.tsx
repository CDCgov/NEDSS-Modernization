import { ReactNode } from 'react';

import styles from './no-results.module.scss';
import { AlertMessage } from 'design-system/message';

type Props = {
    children?: ReactNode;
};

const NoResults = ({ children = 'Try refining your search.' }: Props) => {
    return (
        <div className={styles.noResults}>
            <AlertMessage type="information">
                <div className={styles.noResultsContent}>
                    <span className={styles.header}> No result found</span>
                    <span className={styles.message}>{children}</span>
                </div>
            </AlertMessage>
        </div>
    );
};

export { NoResults };
