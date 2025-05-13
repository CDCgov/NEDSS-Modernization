import { Config } from 'config';
import { useEffect, useState } from 'react';
import { MatchRequiringReviewResponse } from './model/MatchRequiringReview';

export const useMatchesRequiringReview = () => {
    const [loading, setLoading] = useState(false);
    const [response, setResponse] = useState<MatchRequiringReviewResponse>({ matches: [], page: 0, total: 0 });
    const [error, setError] = useState<string | undefined>(undefined);

    useEffect(() => {
        fetchMatchesRequiringReview();
    }, []);

    const fetchMatchesRequiringReview = async (page = 0, pageSize = 20, sort: string | undefined = undefined) => {
        setLoading(true);

        const sortString = sort !== undefined ? `&sort=${sort}` : '';
        fetch(`${Config.deduplicationUrl}/merge?page=${page}&size=${pageSize}${sortString}`, {
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
                        console.error('Failed to extract json for matches requiring review.');
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
        fetchMatchesRequiringReview
    };
};
