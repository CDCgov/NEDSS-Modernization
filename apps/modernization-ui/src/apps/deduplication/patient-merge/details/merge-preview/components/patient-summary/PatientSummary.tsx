import { PatientDescriptor } from 'libs/patient/PatientDescriptor';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import styles from './PatientSummary.module.scss';
import { format, parseISO } from 'date-fns';

type PatientSummaryProps = {
    mergeCandidates: MergeCandidate[];
    mergeFormData: PatientMergeForm;
};

export const PatientSummary = ({ mergeCandidates, mergeFormData }: PatientSummaryProps) => {
    const findCandidateByUid = (uid?: string) => {
        if (!uid) return undefined;
        return mergeCandidates.find((c) => c.personUid === uid);
    };

    const survivingCandidate = findCandidateByUid(mergeFormData.survivingRecord);

    const mostRecentLegalName = mergeFormData.names
        .map(({ personUid, sequence }) =>
            findCandidateByUid(personUid)?.names.find((n) => n.sequence === sequence && n.type === 'Legal')
        )
        .filter((n) => n !== undefined)
        .sort((a, b) => parseISO(b!.asOf).getTime() - parseISO(a!.asOf).getTime())[0];

    const sex = findCandidateByUid(mergeFormData.sexAndBirth?.currentSex)?.sexAndBirth?.currentSex;

    const birthdayRaw = findCandidateByUid(mergeFormData.sexAndBirth?.dateOfBirth)?.sexAndBirth?.dateOfBirth;
    const birthday = birthdayRaw ? format(parseISO(birthdayRaw), 'MM/dd/yyyy') : undefined;

    const descriptor = {
        id: Number(mergeFormData.survivingRecord),
        patientId: Number(survivingCandidate?.personLocalId) || NaN,
        name: mostRecentLegalName,
        status: 'active', // placeholder
        sex,
        birthday
    };

    return (
        <div className={styles.summaryContainer}>
            <PatientDescriptor key={descriptor.id} headingLevel={2} patient={descriptor} />
        </div>
    );
};
