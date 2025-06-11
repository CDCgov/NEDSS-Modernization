import { Config } from 'config';

export const useRemoveMerge = () => {
    const keepAllSeparate = (matchId: string, onSuccess: () => void, onError: () => void) => {
        fetch(`${Config.deduplicationUrl}/merge/${matchId}`, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    onSuccess();
                } else {
                    onError();
                }
            })
            .catch((error) => {
                console.error(error);
                onError();
            });
    };

    const removePatient = (matchId: string, personUid: string, onSuccess: () => void, onError: () => void) => {
        fetch(`${Config.deduplicationUrl}/merge/${matchId}/${personUid}`, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    onSuccess();
                } else {
                    onError();
                }
            })
            .catch((error) => {
                console.error(error);
                onError();
            });
    };

    return {
        removePatient,
        keepAllSeparate
    };
};
