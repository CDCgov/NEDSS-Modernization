import { useState, useEffect } from 'react';
import { FindVaccinationsForPatientQuery, useFindVaccinationsForPatientLazyQuery } from 'generated/graphql/schema';
import { usePagination, Status } from 'pagination';
import { Vaccination } from './PatientVaccination';
import { transform } from './PatientVaccinationTransformer';

export const usePatientProfileVaccinationAPI = (patient?: string): Vaccination[] => {
    const [tracings, setTracings] = useState<Vaccination[]>([]);
    const { page, ready } = usePagination();

    const handleComplete = (data: FindVaccinationsForPatientQuery) => {
        const total = data?.findVaccinationsForPatient?.total || 0;
        const number = data?.findVaccinationsForPatient?.number || 0;
        ready(total, number + 1);

        const content = transform(data?.findVaccinationsForPatient);

        setTracings(content);
    };

    const [getTracings] = useFindVaccinationsForPatientLazyQuery({
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
