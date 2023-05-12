import { Tracing, Headers } from './PatientContacts';
import { PatientContactTable } from './PatientContactTable';

type Props = {
    source: (patient?: string) => Tracing[];
    patient?: string;
    title: string;
    headings: { name: Headers; sortable: boolean }[];
};

export const PatientContactsContainer = ({ source, patient, title, headings }: Props) => (
    <PatientContactTable patient={patient} tracings={source(patient)} title={title} headings={headings} />
);
