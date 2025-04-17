import { useEffect } from 'react';
import { usePagination } from 'pagination';
import { Vaccination } from './PatientVaccination';
import { PatientVaccinationTable } from './PatientVaccinationTable';

type PageAwarePatientDataSource<T> = (patient?: string) => T[];

type Props = {
    source: PageAwarePatientDataSource<Vaccination>;
    patient?: string;
    allowAdd: boolean;
};

export const PatientVaccinationContainer = ({ source, patient, allowAdd }: Props) => {
    const tracing = source(patient);
    const { firstPage } = usePagination();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);

    return <PatientVaccinationTable patient={patient} vaccinations={tracing} allowAdd={allowAdd} />;
};
