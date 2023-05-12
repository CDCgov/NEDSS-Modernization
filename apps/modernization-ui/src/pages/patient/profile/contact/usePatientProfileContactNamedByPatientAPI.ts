import { useEffect, useState } from 'react';
import { FindContactsNamedByPatientQuery, useFindContactsNamedByPatientLazyQuery } from 'generated/graphql/schema';
import { usePage } from 'page';
import { transform } from './PatientContactTransformer';
import { Tracing } from './PatientContacts';

export const usePatientProfileContactNamedByPatientAPI = (patient?: string) => {
    const [tracings, setTracings] = useState<Tracing[]>([]);

    const { page, dispatch: pageDispatch } = usePage();

    const handleComplete = (data: FindContactsNamedByPatientQuery) => {
        const total = data?.findContactsNamedByPatient?.total || 0;
        const pageNumber = data?.findContactsNamedByPatient?.number || 0;
        pageDispatch({ type: 'ready', total: total, page: pageNumber + 1 });

        const content = transform(data?.findContactsNamedByPatient);

        setTracings(content);
    };

    const [getTracings] = useFindContactsNamedByPatientLazyQuery({ onCompleted: handleComplete });

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
