import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Shown } from 'conditional-render';
import { MergeReview } from './merge-review/MergeReview';
import { MergePreview } from './merge-preview/MergePreview';
import { useMergeDetails } from 'apps/deduplication/api/useMergeDetails';
import styles from './MergeDetails.module.scss';
import { Loading } from 'components/Spinner';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientMergeForm } from './merge-review/model/PatientMergeForm';

export const MergeDetails = () => {
    const [pageState, setPageState] = useState<'review' | 'preview'>('review');
    const { patientId } = useParams();
    const { response, loading, fetchPatientMergeDetails } = useMergeDetails();
    const form = useForm<PatientMergeForm>({ mode: 'onBlur' });

    useEffect(() => {
        if (patientId !== undefined) {
            fetchPatientMergeDetails(patientId);
        }
    }, [patientId]);

    const handleRemovePatient = (personUid: string) => {
        console.log('Remove patient NYI', personUid);
    };

    return (
        <div className={styles.mergeDetails}>
            <Shown when={loading === false} fallback={<Loading center />}>
                <FormProvider {...form}>
                    <Shown when={pageState === 'review'}>
                        <MergeReview
                            patientData={response ?? []}
                            onPreview={() => setPageState('preview')}
                            onRemovePatient={handleRemovePatient}
                        />
                    </Shown>
                    <Shown when={pageState === 'preview'}>
                        <MergePreview onBack={() => setPageState('review')} />
                    </Shown>
                </FormProvider>
            </Shown>
        </div>
    );
};
