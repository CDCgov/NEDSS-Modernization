import { useState, useEffect } from 'react';
import { FindPatientNamedByContactQuery, useFindPatientNamedByContactLazyQuery } from 'generated/graphql/schema';
import { usePagination, Status } from 'pagination';
import { Tracing } from './PatientContacts';
import { transform } from './PatientContactTransformer';

export const usePatientProfilePatientNamedByContactAPI = (patient?: string): Tracing[] => {
    const [tracings, setTracings] = useState<Tracing[]>([]);
    const { page, ready } = usePagination();

    const handleComplete = (data: FindPatientNamedByContactQuery) => {
        const total = data?.findPatientNamedByContact?.total || 0;
        const number = data?.findPatientNamedByContact?.number || 0;
        ready(total, number + 1);

        const content = transform(data?.findPatientNamedByContact);

        setTracings(content);
    };

    const [getTracings] = useFindPatientNamedByContactLazyQuery({
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
