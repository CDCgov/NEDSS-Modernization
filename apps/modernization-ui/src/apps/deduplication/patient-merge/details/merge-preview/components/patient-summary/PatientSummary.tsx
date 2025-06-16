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
    const selectedPersonUid = mergeFormData.survivingRecord;
    const findCandidateByUid = (uid: string) => mergeCandidates.find((c) => c.personUid === uid);
    const survivingCandidate = findCandidateByUid(selectedPersonUid);
    const selectedLegalNames = mergeFormData.names
        .map(({ personUid, sequence }) => {
            const candidate = findCandidateByUid(personUid);
            return candidate?.names.find((n) => n.sequence === sequence && n.type === 'Legal'); // L for Legal
        })
        .filter((name): name is NonNullable<typeof name> => !!name && !!name.asOf);

    // Sort descending by asOf date
    selectedLegalNames.sort((a, b) => {
        const dateA = parseISO(a.asOf ?? '');
        const dateB = parseISO(b.asOf ?? '');
        return dateB.getTime() - dateA.getTime();
    });

    const mostRecentLegalName = selectedLegalNames[0]; // This will be the fullName for display

    const sexCandidate = mergeFormData.sexAndBirth?.currentSex
        ? findCandidateByUid(mergeFormData.sexAndBirth.currentSex)
        : undefined;

    const dateOfBirthCandidate = mergeFormData.sexAndBirth?.dateOfBirth
        ? findCandidateByUid(mergeFormData.sexAndBirth.dateOfBirth)
        : undefined;

    const formattedDate = dateOfBirthCandidate?.sexAndBirth?.dateOfBirth
        ? format(parseISO(dateOfBirthCandidate.sexAndBirth.dateOfBirth), 'MM/dd/yyyy')
        : undefined;

    const descriptor = {
        id: parseInt(mergeFormData.survivingRecord, 10),
        patientId: survivingCandidate ? parseInt(survivingCandidate.personLocalId, 10) : NaN,
        name: mostRecentLegalName,
        status: 'active',
        sex: sexCandidate?.sexAndBirth?.currentSex,
        birthday: formattedDate
    };

    return (
        <div className={styles.summaryContainer}>
            <PatientDescriptor key={descriptor.id} headingLevel={2} patient={descriptor} />
        </div>
    );
};
