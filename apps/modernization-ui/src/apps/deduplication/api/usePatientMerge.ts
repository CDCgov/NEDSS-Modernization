import { Config } from 'config';
import { PatientMergeForm } from '../patient-merge/details/merge-review/model/PatientMergeForm';

export const usePatientMerge = () => {
    const mergePatients = (
        mergeForm: PatientMergeForm,
        groupId: string,
        onSuccess: () => void,
        onError: () => void
    ) => {
        fetch(`${Config.deduplicationUrl}/merge/${groupId}`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(mergeForm)
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

    return { mergePatients };
};
