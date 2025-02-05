import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import styles from './no-patient-result.module.scss';
import { useAddPatientFromSearch } from 'apps/search/patient/add/useAddPatientFromSearch';
import { PatientSearchResultTable } from '../table';

const NoPatientResults = () => {
    const { add } = useAddPatientFromSearch();

    return (
        <>
            <PatientSearchResultTable results={[]} />
            <div className={styles.noResults}>
                <AlertBanner type="info" iconSize={4}>
                    <div className={styles.noResultsContent}>
                        <span className={styles.noResultsHeader}> No result found</span>
                        <span className={styles.noResultsSubHeading}>
                            Try refining your search, or{' '}
                            <a className={styles.link} onClick={add}>
                                add a new patient.
                            </a>
                        </span>
                    </div>
                </AlertBanner>
            </div>
        </>
    );
};

export { NoPatientResults };
