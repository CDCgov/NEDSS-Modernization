import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ReactNode } from 'react';

import styles from './no-results.module.scss';

type Props = {
    children?: ReactNode;
};

const NoResults = ({ children = 'Try refining your search.' }: Props) => {
    return (
        <div className={styles.noResults}>
            <AlertBanner type="info" iconSize={4}>
                <div className={styles.noResultsContent}>
                    <span className={styles.header}> No result found</span>
                    <span className={styles.message}>{children}</span>
                </div>
            </AlertBanner>
        </div>
    );
};

export { NoResults };
