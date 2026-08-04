import { useEffect, useState } from 'react';

import { Config } from 'config';
import { logErrorToUserConsole } from 'utils/logging';

import { DataElements } from './model/DataElement';

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<DataElements | undefined>();
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchDataElements = async () => {
        setLoading(true);

        fetch(`${Config.deduplicationUrl}/configuration/data-elements`, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
            },
        })
            .then((response) => {
                response
                    .json()
                    .then((dataElements) => setDataElements(dataElements))
                    .catch(() => {
                        logErrorToUserConsole('Failed to extract json for data elements.');
                    });
            })
            .catch((error) => {
                logErrorToUserConsole(error);
                setError('Failed to retrieve data elements');
            })
            .finally(() => setLoading(false));
    };

    const save = async (updatedDataElements: DataElements, successCallback?: () => void) => {
        fetch(`${Config.deduplicationUrl}/configuration/data-elements`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json',
            },
            body: JSON.stringify(updatedDataElements),
        })
            .then((response) => {
                response
                    .json()
                    .then((dataElements) => setDataElements(dataElements))
                    .catch(() => {
                        logErrorToUserConsole('Failed to extract json for data elements.');
                    });
            })
            .catch((error) => {
                logErrorToUserConsole(error);
                setError('Failed to save data elements');
            });

        successCallback?.();
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, save, loading, error, dataElements };
};
