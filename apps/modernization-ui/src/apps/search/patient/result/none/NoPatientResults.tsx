import styles from './no-patient-result.module.scss';
import { useAddPatientFromSearch } from 'apps/search/patient/add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';
import { AlertMessage } from 'design-system/message';
import { Button } from 'design-system/button';

const NoPatientResults = () => {
    const { add } = useAddPatientFromSearch();

    return (
        <div>
            <div className={styles.noResults} role="alert" id="no-patient-results-alert">
                <AlertMessage type="information">
                    <div className={styles.noResultsContent} role="alert">
                        <span className={styles.noResultsHeader}> No result found</span>
                        <span className={styles.noResultsSubHeading}>
                            <Permitted permission={permissions.patient.add} fallback="Try refining your search.">
                                <span aria-label=" Try refining your search, or add a new patient.">
                                    Try refining your search, or{' '}
                                    <Button
                                        className={styles.link}
                                        onClick={add}
                                        tertiary
                                        aria-label="add a new patient">
                                        add a new patient.
                                    </Button>
                                </span>
                            </Permitted>
                        </span>
                    </div>
                </AlertMessage>
            </div>
        </div>
    );
};

export { NoPatientResults };
