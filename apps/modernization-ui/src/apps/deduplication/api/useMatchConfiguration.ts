import { useEffect, useState } from 'react';
import { Pass } from './model/Pass';
import { Config } from 'config';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        fetch(`${Config.deduplicationUrl}/api/configuration`, {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm) => setPasses(algorithm.passes))
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.log(error);
                setError('Failed to retrieve pass configuration');
            });

        setLoading(false);
    };

    const deletePass = (id: number, successCallback?: () => void) => {
        fetch(`${Config.deduplicationUrl}/api/configuration/pass/${id}`, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm) => setPasses(algorithm.passes))
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.log(error);
                setError('Failed to delete pass');
            });

        successCallback?.();
    };

    const savePass = (pass: Pass, successCallback?: () => void) => {
        fetch(`${Config.deduplicationUrl}/api/configuration/pass`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(pass)
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm) => setPasses(algorithm.passes))
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.log(error);
                setError('Failed to save pass');
            });

        successCallback?.();
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return { fetchConfiguration, deletePass, savePass, loading, error, passes };
};
