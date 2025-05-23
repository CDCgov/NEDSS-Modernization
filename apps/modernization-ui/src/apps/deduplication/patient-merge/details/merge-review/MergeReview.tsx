import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { useNavigate } from 'react-router';
import styles from './merge-review.module.scss';
import { PatientIdSelection } from './patient-form/patient-id/PatientIdSelection';
import { AdminCommentsSelection } from './patient-form/admin-comments/AdminCommentsSelection';

export type Props = {
    patientData: PatientData[];
    onPreview: () => void;
    onRemovePatient: (personUid: string) => void;
};
export const MergeReview = ({ patientData, onPreview, onRemovePatient }: Props) => {
    const nav = useNavigate();

    return (
        <div className={styles.mergeReview}>
            <header>
                <Heading level={1}>Patient matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button secondary onClick={() => nav('/deduplication/merge')}>
                        Back
                    </Button>
                    <Button secondary onClick={onPreview}>
                        Preview merge
                    </Button>
                    <Button onClick={() => console.log('Keep all separate NYI')}>Keep all separate</Button>
                    <Button onClick={() => console.log('Merge all NYI')}>Merge all</Button>
                </div>
            </header>
            <main>
                <div className={styles.infoText}>
                    Only one record is selected for Patient ID. By default, the oldest record is selected as the
                    surviving ID. If this is not correct, select the appropriate record.
                </div>
                <div className={styles.patientContent}>
                    <PatientIdSelection patientData={patientData} onRemovePatient={onRemovePatient} />
                    <AdminCommentsSelection patientData={patientData} />
                </div>
            </main>
        </div>
    );
};
