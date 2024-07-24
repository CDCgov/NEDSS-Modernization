import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import styles from './no-patient-result-banner.module.scss';

export const NoResultsBanner = () => {
    return (
        <div className={styles.noResults}>
            <AlertBanner type="info" iconSize={4}>
                <div className={styles.noResultsContent}>
                    <span className={styles.noResultsHeader}> No result found</span>
                    <span className={styles.noResultsSubHeading}>Try refining your search.</span>
                </div>
            </AlertBanner>
        </div>
    );
};
