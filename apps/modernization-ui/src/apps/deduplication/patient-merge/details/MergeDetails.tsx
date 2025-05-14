import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Shown } from 'conditional-render';
import { MergeReview } from './merge-review/MergeReview';
import { useMergeDetails } from 'apps/deduplication/api/useMergeDetails';
import styles from './MergeDetails.module.scss';
import { Loading } from 'components/Spinner';

export const MergeDetails = () => {
    const [pageState] = useState<'review' | 'preview'>('review');
    const { patientId } = useParams();
    const { loading, fetchPatientMergeDetails } = useMergeDetails();

    useEffect(() => {
        if (patientId !== undefined) {
            fetchPatientMergeDetails(patientId);
        }
    }, [patientId]);

    return (
        <div className={styles.mergeDetails}>
            <Shown when={loading === false} fallback={<Loading center />}>
                <Shown when={pageState === 'review'}>
                    <MergeReview />
                </Shown>
            </Shown>
        </div>
    );
};
