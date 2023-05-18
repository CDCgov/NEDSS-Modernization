import { useEffect } from 'react';
import { usePage } from 'page';
import { Tracing, Headers } from './PatientContacts';
import { PatientContactTable } from './PatientContactTable';

type PageAwarePatientDataSource<T> = (patient?: string) => T[];

type Props = {
    source: PageAwarePatientDataSource<Tracing>;
    patient?: string;
    title: string;
    headings: { name: Headers; sortable: boolean }[];
};

export const PatientContactsContainer = ({ source, patient, title, headings }: Props) => {
    const tracing = source(patient);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);

    return <PatientContactTable patient={patient} tracings={tracing} title={title} headings={headings} />;
};
