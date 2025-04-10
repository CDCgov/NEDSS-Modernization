import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import styles from './no-patient-result.module.scss';
import { useAddPatientFromSearch } from 'apps/search/patient/add/useAddPatientFromSearch';
import { PatientSearchResultTable } from '../table';
import { Sizing } from 'design-system/field';
import { permissions, Permitted } from 'libs/permission';

type Props = { sizing: Sizing };

const NoPatientResults = ({ sizing }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <>
            <PatientSearchResultTable results={[]} sizing={sizing} />
            <div className={styles.noResults}>
                <AlertBanner type="info" iconSize={4}>
                    <div className={styles.noResultsContent}>
                        <span className={styles.noResultsHeader}> No result found</span>
                        <span className={styles.noResultsSubHeading}>
                            <Permitted include={[permissions.patient.add]} fallback="Try refining your search.">
                                Try refining your search, or{' '}
                                <a className={styles.link} onClick={add}>
                                    add a new patient.
                                </a>
                            </Permitted>
                        </span>
                    </div>
                </AlertBanner>
            </div>
        </>
    );
};

export { NoPatientResults };
