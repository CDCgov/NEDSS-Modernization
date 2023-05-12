import { useEffect, useState } from 'react';
import { FindPatientNamedByContactQuery, useFindPatientNamedByContactLazyQuery } from 'generated/graphql/schema';
import { usePage } from 'page';
import { transform } from './PatientContactTransformer';
import { Tracing } from './PatientContacts';

export const usePatientProfilePatientNamedByContactAPI = (patient?: string) => {
    const [tracings, setTracings] = useState<Tracing[]>([]);

    const { page, dispatch: pageDispatch } = usePage();

    const handleComplete = (data: FindPatientNamedByContactQuery) => {
        const total = data?.findPatientNamedByContact?.total || 0;
        const pageNumber = data?.findPatientNamedByContact?.number || 0;
        pageDispatch({ type: 'ready', total: total, page: pageNumber + 1 });

        const content = transform(data?.findPatientNamedByContact);

        setTracings(content);
    };

    const [getTracings] = useFindPatientNamedByContactLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
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
    }, [patient, page.current]);

    return tracings;
};
