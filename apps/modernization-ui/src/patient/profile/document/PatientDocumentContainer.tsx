import { Document } from './PatientDocuments';
import { PatientDocumentTable } from './PatientDocumentTable';

type Props = {
    source: (patient?: string) => Document[];
    nbsBase: string;
    patient?: string;
};

export const PatientDocumentContainer = ({ source, nbsBase, patient }: Props) => (
    <PatientDocumentTable nbsBase={nbsBase} documents={source(patient)} />
);
