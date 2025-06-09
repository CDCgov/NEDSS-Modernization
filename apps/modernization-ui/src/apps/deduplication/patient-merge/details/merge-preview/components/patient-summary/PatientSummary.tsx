import { PatientDescriptor } from 'libs/patient/PatientDescriptor';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import styles from './PatientSummary.module.scss';
import { DisplayableName } from '../../../../../../../name';
import { toMMDDYYYY } from '../../utils/dateUtils';

type PatientSummaryProps = {
    mergeCandidates: MergeCandidate[];
    mergeFormData: PatientMergeForm;
};

export const PatientSummary = ({ mergeFormData, mergeCandidates }: PatientSummaryProps) => {
    const findCandidateByUid = (uid: string) => mergeCandidates.find((c) => c.personUid === uid);
    const selectedNameEntry =
        mergeFormData.names.find((name) => name.personUid === mergeFormData.survivingRecord) || mergeFormData.names[0];
    const selectedPersonUid = selectedNameEntry?.personUid ?? mergeFormData.survivingRecord;
    const nameCandidate = mergeCandidates.find((c) => c.personUid === selectedPersonUid);
    const survivingCandidate = mergeCandidates.find((c) => c.personUid === selectedPersonUid);
    const name: DisplayableName = {
        first: nameCandidate?.names?.[0]?.first || '',
        middle: nameCandidate?.names?.[0]?.middle || '',
        last: nameCandidate?.names?.[0]?.last || '',
        suffix: nameCandidate?.names?.[0]?.suffix || '',
        type: nameCandidate?.names?.[0]?.type || ''
    };

    const sexCandidate = mergeFormData.sexAndBirth?.currentSex
        ? findCandidateByUid(mergeFormData.sexAndBirth.currentSex)
        : undefined;

    const dateOfBirthCandidate = mergeFormData.sexAndBirth?.dateOfBirth
        ? findCandidateByUid(mergeFormData.sexAndBirth.dateOfBirth)
        : undefined;

    const descriptor = {
        id: parseInt(mergeFormData.survivingRecord, 10),
        patientId: survivingCandidate ? parseInt(survivingCandidate.personLocalId, 10) : NaN,
        name: name,
        status: 'active',
        sex: sexCandidate?.sexAndBirth?.currentSex || '',
        birthday: toMMDDYYYY(dateOfBirthCandidate?.sexAndBirth?.dateOfBirth || '')
    };

    return (
        <div className={styles.summaryContainer}>
            <PatientDescriptor key={descriptor.id} headingLevel={2} patient={descriptor} />
        </div>
    );
};
