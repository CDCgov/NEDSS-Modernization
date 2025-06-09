import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { useMergeDetails } from 'apps/deduplication/api/useMergeDetails';
import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { parseISO } from 'date-fns/fp';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useParams } from 'react-router';
import { MergePreview } from './merge-preview/MergePreview';
import { MergeReview } from './merge-review/MergeReview';
import {
    AddressId,
    IdentificationId,
    NameId,
    PatientMergeForm,
    PhoneEmailId,
    RaceId
} from './merge-review/model/PatientMergeForm';
import styles from './MergeDetails.module.scss';

export const MergeDetails = () => {
    const [pageState, setPageState] = useState<'review' | 'preview'>('review');
    const { matchId } = useParams();
    const { response, loading, fetchPatientMergeDetails } = useMergeDetails();
    const form = useForm<PatientMergeForm>({ mode: 'onBlur' });

    useEffect(() => {
        if (matchId !== undefined) {
            fetchPatientMergeDetails(matchId);
        }
    }, [matchId]);

    useEffect(() => {
        if (response && response.length > 0) {
            const oldestRecord = response.reduce((a, b) =>
                parseISO(a.addTime).getTime() < parseISO(b.addTime).getTime() ? a : b
            );
            if (oldestRecord) {
                setDefaultValues(oldestRecord.personUid, response);
            }
        }
    }, [response]);

    const setDefaultValues = (oldestRecordId: string, mergeCandidates: MergeCandidate[]) => {
        const nameValues: NameId[] = mergeCandidates.flatMap((m) =>
            m.names.map((n) => {
                return { personUid: n.personUid, sequence: n.sequence };
            })
        );
        const addressValues: AddressId[] = mergeCandidates.flatMap((m) =>
            m.addresses.map((a) => {
                return { locatorId: a.id };
            })
        );
        const phoneEmalValues: PhoneEmailId[] = mergeCandidates.flatMap((m) =>
            m.phoneEmails.map((p) => {
                return { locatorId: p.id };
            })
        );
        const identificationValues: IdentificationId[] = mergeCandidates.flatMap((m) =>
            m.identifications.map((n) => {
                return { personUid: n.personUid, sequence: n.sequence };
            })
        );
        const raceValues: RaceId[] = mergeCandidates.flatMap((m) =>
            m.races.map((r) => {
                return { personUid: r.personUid, raceCode: r.raceCode };
            })
        );

        form.reset(
            {
                survivingRecord: oldestRecordId,
                adminComments: oldestRecordId,
                names: nameValues,
                addresses: addressValues,
                phoneEmails: phoneEmalValues,
                identifications: identificationValues,
                races: raceValues,
                ethnicity: oldestRecordId,
                sexAndBirth: {
                    asOf: oldestRecordId,
                    dateOfBirth: oldestRecordId,
                    currentSex: oldestRecordId,
                    transgenderInfo: oldestRecordId,
                    additionalGender: oldestRecordId,
                    birthGender: oldestRecordId,
                    multipleBirth: oldestRecordId,
                    birthCity: oldestRecordId,
                    birthState: oldestRecordId,
                    birthCountry: oldestRecordId
                },
                mortality: {
                    asOf: oldestRecordId,
                    deceased: oldestRecordId,
                    dateOfDeath: oldestRecordId,
                    deathCity: oldestRecordId,
                    deathState: oldestRecordId,
                    deathCountry: oldestRecordId
                },
                generalInfo: {
                    asOf: oldestRecordId,
                    maritalStatus: oldestRecordId,
                    mothersMaidenName: oldestRecordId,
                    numberOfAdultsInResidence: oldestRecordId,
                    numberOfChildrenInResidence: oldestRecordId,
                    primaryOccupation: oldestRecordId,
                    educationLevel: oldestRecordId,
                    primaryLanguage: oldestRecordId,
                    speaksEnglish: oldestRecordId,
                    stateHivCaseId: oldestRecordId
                }
            },
            { keepDefaultValues: false }
        );
    };

    const handleRemovePatient = (personUid: string) => {
        console.log('Remove patient NYI', personUid);
    };

    return (
        <div className={styles.mergeDetails}>
            <Shown when={loading === false} fallback={<Loading center />}>
                <FormProvider {...form}>
                    <Shown when={pageState === 'review'}>
                        <MergeReview
                            mergeCandidates={response ?? []}
                            onPreview={() => setPageState('preview')}
                            onRemovePatient={handleRemovePatient}
                        />
                    </Shown>
                    <Shown when={pageState === 'preview'}>
                        <MergePreview
                            mergeCandidates={response ?? []}
                            mergeFormData={form.getValues()}
                            onBack={() => setPageState('review')}
                        />
                    </Shown>
                </FormProvider>
            </Shown>
        </div>
    );
};
