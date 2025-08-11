import { useEffect } from 'react';
import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';

export function usePatientMergeQueueStatus(personUid?: string) {
    const { data, error, isLoading, load } = useApi({
        resolver: PatientFileService.isPatientInMergeQueue
    });

    useEffect(() => {
        if (personUid) {
            load(personUid);
        }
    }, [personUid, load]);

    return {
        inMergeQueue: data?.inMergeQueue ?? null,
        mergeGroup: data?.mergeGroup ?? null,
        loading: isLoading,
        error
    };
}
