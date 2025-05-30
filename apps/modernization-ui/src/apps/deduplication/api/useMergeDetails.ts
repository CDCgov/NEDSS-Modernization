import { Config } from 'config';
import { useState } from 'react';
import { MergePatient } from './model/MergePatient';

export const useMergeDetails = () => {
    const [loading, setLoading] = useState(false);
    const [response, setResponse] = useState<MergePatient[] | undefined>();
    const [error, setError] = useState<string | undefined>(undefined);

    const fetchPatientMergeDetails = (patientId: string) => {
        setLoading(true);

        fetch(`${Config.deduplicationUrl}/merge/${patientId}`, {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                response
                    .json()
                    .then((response) => {
                        setResponse(response);
                    })
                    .catch(() => {
                        console.error('Failed to extract json for patient merge details.');
                    });
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to retrieve matches requiring review');
            })
            .finally(() => setLoading(false));
    };

    return {
        loading,
        error,
        response,
        fetchPatientMergeDetails
    };
};
