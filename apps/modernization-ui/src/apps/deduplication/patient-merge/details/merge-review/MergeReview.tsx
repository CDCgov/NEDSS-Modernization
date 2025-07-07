import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { useNavigate, useParams } from 'react-router';
import styles from './merge-review.module.scss';
import { AdminCommentsSelection } from './patient-form/admin-comments/AdminCommentsSelection';
import { NameSelection } from './patient-form/name/NameSelection';
import { PatientIdSelection } from './patient-form/patient-id/PatientIdSelection';
import { AddressSelection } from './patient-form/address/AddressSelection';
import { PhoneEmailSelection } from './patient-form/phone-email/PhoneEmailSelection';
import { IdentificationSelection } from './patient-form/identification/IdentificationSelection';
import { RaceSelection } from './patient-form/race/RaceSelection';
import { EthnicitySelection } from './patient-form/ethnicity/EthnicitySelection';
import { SexAndBirthSelection } from './patient-form/sex-and-birth/SexAndBirthSelection';
import { MortalitySelection } from './patient-form/mortality/MortalitySelection';
import { GeneralSelection } from './patient-form/general/GeneralSelection';
import { InvestigationDisplay } from './patient-form/investigations/InvestigationsDisplay';
import { useAlert } from 'libs/alert';
import { useRemoveMerge } from 'apps/deduplication/api/useRemoveMerge';

export type Props = {
    mergeCandidates: MergeCandidate[];
    onPreview: () => void;
    onRemovePatient: (personUid: string) => void;
    onMerge: () => void;
};
export const MergeReview = ({ mergeCandidates, onPreview, onRemovePatient, onMerge }: Props) => {
    const { matchId } = useParams();
    const { keepAllSeparate } = useRemoveMerge();
    const nav = useNavigate();
    const { showAlert, showError } = useAlert();

    const handleKeepAllSeparate = () => {
        if (matchId !== undefined) {
            keepAllSeparate(matchId, handleKeepSeparateSuccess, handleKeepSeparateError);
        }
    };

    const handleKeepSeparateSuccess = () => {
        const patientIds = mergeCandidates.map((m) => m.personLocalId).join(',');
        showAlert({
            type: 'success',
            title: 'Success',
            message: (
                <span>
                    You have chosen to keep the following patients separate: <strong>{patientIds}.</strong> They have
                    been removed from the matches requiring review.
                </span>
            )
        });
        nav('/deduplication/merge');
    };

    const handleKeepSeparateError = () => {
        showError('Failed to remove patients from merge.');
    };

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
                    <Button onClick={handleKeepAllSeparate}>Keep all separate</Button>
                    <Button onClick={onMerge}>Merge all</Button>
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
                    <SexAndBirthSelection mergeCandidates={mergeCandidates} />
                    <MortalitySelection mergeCandidates={mergeCandidates} />
                    <GeneralSelection mergeCandidates={mergeCandidates} />
                    <InvestigationDisplay mergeCandidates={mergeCandidates} />
                </div>
            </main>
        </div>
    );
};
