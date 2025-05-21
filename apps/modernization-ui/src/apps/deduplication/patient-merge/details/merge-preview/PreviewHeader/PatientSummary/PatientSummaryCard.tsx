import styles from './patient-summary-card.module.scss';

type PatientSummaryCardProps = {
    firstName: string;
    lastName: string;
    dob: string;
    age: string | number;
    gender?: string;
    patientId: string;
    separator?: boolean;
};

export const PatientSummaryCard = ({
    firstName,
    lastName,
    dob,
    age,
    gender,
    patientId,
    separator = true
}: PatientSummaryCardProps) => {
    const Separator = ({ show }: { show: boolean }) => (show ? <span className={styles.separator}>|</span> : null);

    return (
        <div className={styles.card}>
            <span className={styles.name}>{`${lastName}, ${firstName}`}</span>
            <Separator show={separator} />
            <span>{gender}</span>
            <Separator show={separator} />
            <span>{`${dob} (${age} years)`}</span>
            <Separator show={separator} />
            <span>{`Patient ID: ${patientId}`}</span>
        </div>
    );
};
