import { BasicInformation } from './BasicInformation';
import { Accordion } from 'components/Accordion/Accordion';
import styles from './patient-criteria.module.scss';

export const PatientCriteria = () => {
    return (
        <div className={styles.criteria}>
            <Accordion title="Basic information" open>
                <BasicInformation />
            </Accordion>
        </div>
    );
};
