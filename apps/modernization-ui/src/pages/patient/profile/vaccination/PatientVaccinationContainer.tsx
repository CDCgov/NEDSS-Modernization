import { useEffect } from 'react';
import { usePage } from 'page';
import { Vaccination } from './PatientVaccination';
import { PatientVaccinationTable } from './PatientVaccinationTable';

type PageAwarePatientDataSource<T> = (patient?: string) => T[];

type Props = {
    source: PageAwarePatientDataSource<Vaccination>;
    patient?: string;
};

export const PatientVaccinationContainer = ({ source, patient }: Props) => {
    const tracing = source(patient);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);

    return <PatientVaccinationTable patient={patient} vaccinations={tracing} />;
};
