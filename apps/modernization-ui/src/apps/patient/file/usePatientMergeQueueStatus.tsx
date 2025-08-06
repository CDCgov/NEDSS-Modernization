import { useState, useEffect } from 'react';
import { PatientFileService } from 'generated';

export function usePatientMergeQueueStatus(personUid?: string) {
    const [inMergeQueue, setInMergeQueue] = useState<boolean | null>(null);
    const [mergeGroup, setMergeGroup] = useState<number | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        if (!personUid) {
            setInMergeQueue(null);
            setMergeGroup(null);
            return;
        }

        setLoading(true);
        setError(null);

        PatientFileService.isPatientInMergeQueue(personUid)
            .then((res) => {
                setInMergeQueue(res.inMergeQueue);
                setMergeGroup(res.mergeGroup ?? null);
            })
            .catch((err) => setError(err))
            .finally(() => setLoading(false));
    }, [personUid]);

    return { inMergeQueue, mergeGroup, loading, error };
}
