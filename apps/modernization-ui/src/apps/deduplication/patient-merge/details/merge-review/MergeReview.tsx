import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { useNavigate } from 'react-router';
import styles from './merge-review.module.scss';
import { AdminCommentsSelection } from './patient-form/admin-comments/AdminCommentsSelection';
import { NameSelection } from './patient-form/name/NameSelection';
import { PatientIdSelection } from './patient-form/patient-id/PatientIdSelection';
import { AddressSelection } from './patient-form/address/AddressSelection';
import { PhoneEmailSelection } from './patient-form/phone-email/PhoneEmailSelection';
import { IdentificationSelection } from './patient-form/identification/IdentificationSelection';
import { RaceSelection } from './patient-form/race/RaceSelection';
import { EthnicitySelection } from './patient-form/ethnicity/EthnicitySelection';

export type Props = {
    mergeCandidates: MergeCandidate[];
    onPreview: () => void;
    onRemovePatient: (personUid: string) => void;
};
export const MergeReview = ({ mergeCandidates, onPreview, onRemovePatient }: Props) => {
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
                    <PatientIdSelection mergeCandidates={mergeCandidates} onRemovePatient={onRemovePatient} />
                    <AdminCommentsSelection mergeCandidates={mergeCandidates} />
                    <NameSelection mergeCandidates={mergeCandidates} />
                    <AddressSelection mergeCandidates={mergeCandidates} />
                    <PhoneEmailSelection mergeCandidates={mergeCandidates} />
                    <IdentificationSelection mergeCandidates={mergeCandidates} />
                    <RaceSelection mergeCandidates={mergeCandidates} />
                    <EthnicitySelection mergeCandidates={mergeCandidates} />
                </div>
            </main>
        </div>
    );
};
