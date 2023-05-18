import { useEffect, useState } from 'react';
import { FindContactsNamedByPatientQuery, useFindContactsNamedByPatientLazyQuery } from 'generated/graphql/schema';
import { Status, usePage } from 'page';
import { Tracing } from './PatientContacts';
import { transform } from './PatientContactTransformer';

export const usePatientProfileContactNamedByPatientAPI = (patient?: string): Tracing[] => {
    const [tracings, setTracings] = useState<Tracing[]>([]);
    const { page, ready } = usePage();

    const handleComplete = (data: FindContactsNamedByPatientQuery) => {
        const total = data?.findContactsNamedByPatient?.total || 0;
        const number = data?.findContactsNamedByPatient?.number || 0;
        ready(total, number + 1);

        const content = transform(data?.findContactsNamedByPatient);

        setTracings(content);
    };

    const [getTracings] = useFindContactsNamedByPatientLazyQuery({
        onCompleted: handleComplete,
        fetchPolicy: 'no-cache'
    });

    useEffect(() => {
        if (patient && page.status === Status.Requested) {
            getTracings({
                variables: {
                    patient: patient,
                    page: {
                        pageNumber: page.current - 1,
                        pageSize: page.pageSize
                    }
                }
            });
        }
    }, [patient, page]);

    return tracings;
};
