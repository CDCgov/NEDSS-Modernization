import { useEffect } from 'react';
import { useApi } from 'libs/api';
import { Config } from 'config';

export function usePatientMergeQueueStatus(personUid?: string) {
    const fetchMergeStatus = (uid: string) =>
        fetch(`${Config.deduplicationUrl}/merge/status/${encodeURIComponent(uid)}`, {
            method: 'GET',
            headers: { Accept: 'application/json' },
            credentials: 'include'
        }).then((res) => {
            if (!res.ok) throw new Error('Failed to fetch merge status');
            return res.json();
        });

    const { data, error, isLoading, load } = useApi({ resolver: fetchMergeStatus });

    useEffect(() => {
        if (personUid) load(personUid);
    }, [personUid, load]);

    return {
        inMergeQueue: data?.inMergeQueue ?? null,
        mergeGroup: data?.mergeGroup ?? null,
        loading: isLoading,
        error
    };
}
