import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import styles from './no-patient-result-banner.module.scss';
import { Link } from 'react-router-dom';

export const NoPatientResultsBanner = () => {
    return (
        <div className={styles.noResults}>
            <AlertBanner type="info" iconSize={4}>
                <div className={styles.noResultsContent}>
                    <span className={styles.noResultsHeader}> No result found</span>
                    <span className={styles.noResultsSubHeading}>
                        Try refining your search, or
                        <Link className={styles.link} to="/add-patient">
                            {' '}
                            add a new patient.
                        </Link>
                    </span>
                </div>
            </AlertBanner>
        </div>
    );
};
