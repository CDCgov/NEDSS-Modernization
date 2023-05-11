import { Document } from './PatientDocuments';
import { PatientDocumentTable } from './PatientDocumentTable';

type Props = {
    source: (patient?: string) => Document[];
    patient?: string;
};

export const PatientDocumentContainer = ({ source, patient }: Props) => (
    <PatientDocumentTable patient={patient} documents={source(patient)} />
);
