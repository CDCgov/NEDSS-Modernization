import { Config } from 'config';
import { PatientInvestigation } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';

export const useFindOpenInvestigations = (patientId: string) => {
    const [loading, setLoading] = useState(false);
    const [response, setResponse] = useState<PatientInvestigation[]>([]);
    const [error, setError] = useState<string | undefined>(undefined);

    useEffect(() => {
        fetchOpenInvestigations();
    }, []);

    const fetchOpenInvestigations = async () => {
        setLoading(true);

        fetch(`${Config.modernizationUrl}/nbs/api/patient/${patientId}/investigations/open`, {
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
                        console.error("Failed to parse json for patient's open investigations.");
                    });
            })
            .catch((error) => {
                console.error(error);
                setError("Failed to retrieve patient's open investigations.");
            })
            .finally(() => setLoading(false));
    };

    return {
        loading,
        error,
        response,
        fetchOpenInvestigations
    };
};
