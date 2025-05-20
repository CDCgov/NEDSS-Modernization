import { Card } from 'design-system/card';
import { dummyPatientData, patientSummaryDummy } from '../dummyData';
import styles from './summary-title-card.module.scss';

export const SummaryTitleCard = () => {
    const { firstName, lastName, dob, age, gender } = patientSummaryDummy;
    const { patientId } = dummyPatientData;

    return (
        <Card
            id="summary-title-card"
            level={2}
            title={
                <div className={styles.titleContainer}>
                    <span className={styles.name}>{`${lastName}, ${firstName}`}</span>
                    <span className={styles.separator}>|</span>
                    <span>{gender}</span>
                    <span className={styles.separator}>|</span>
                    <span>{`${dob} (${age} years)`}</span>
                    <span className={styles.separator}>|</span>
                    <span>{`Patient ID: ${patientId}`}</span>
                </div>
            }>
            <></>
        </Card>
    );
};
