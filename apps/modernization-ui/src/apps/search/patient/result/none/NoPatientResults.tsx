import styles from './no-patient-result.module.scss';
import { useAddPatientFromSearch } from 'apps/search/patient/add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';
import { AlertMessage } from 'design-system/message';
import { Button } from 'design-system/button';

const NoPatientResults = () => {
    const { add } = useAddPatientFromSearch();

    return (
        <div>
            <div className={styles.noResults}>
                <AlertMessage
                    type="information"
                    title="No results found"
                    aria-label="Try refining your search, or add a new patient.">
                    <div className={styles.noResultsContent}>
                        <Permitted permission={permissions.patient.add} fallback="Try refining your search.">
                            <span className={styles.noResultsSubHeading}>
                                Try refining your search, or{' '}
                                <Button className={styles.link} onClick={add} tertiary>
                                    add a new patient.
                                </Button>
                            </span>
                        </Permitted>
                    </div>
                </AlertMessage>
            </div>
        </div>
    );
};

export { NoPatientResults };
