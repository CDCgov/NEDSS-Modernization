import { Config } from 'config';

export const useRemoveMerge = () => {
    const keepAllSeparate = (groupId: string, onSuccess: () => void, onError: () => void) => {
        fetch(`${Config.deduplicationUrl}/merge/${groupId}`, {
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

    const removePatient = (groupId: string, personUid: string, onSuccess: () => void, onError: () => void) => {
        fetch(`${Config.deduplicationUrl}/merge/${groupId}/${personUid}`, {
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
