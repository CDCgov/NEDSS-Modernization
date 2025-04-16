import styles from './no-patient-result.module.scss';
import { useAddPatientFromSearch } from 'apps/search/patient/add/useAddPatientFromSearch';
import { PatientSearchResultTable } from '../table';
import { Sizing } from 'design-system/field';
import { permissions, Permitted } from 'libs/permission';
import { AlertMessage } from 'design-system/message';

type Props = { sizing: Sizing };

const NoPatientResults = ({ sizing }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <>
            <PatientSearchResultTable results={[]} sizing={sizing} />
            <div className={styles.noResults}>
                <AlertMessage type="information">
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
                </AlertMessage>
            </div>
        </>
    );
};

export { NoPatientResults };
