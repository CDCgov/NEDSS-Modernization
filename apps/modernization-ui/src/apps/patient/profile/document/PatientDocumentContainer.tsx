import { useEffect } from 'react';
import { usePagination } from 'pagination';
import { Document } from './PatientDocuments';
import { PatientDocumentTable } from './PatientDocumentTable';

type Props = {
    source: (patient?: string) => Document[];
    patient?: string;
};

export const PatientDocumentContainer = ({ source, patient }: Props) => {
    const documents = source(patient);
    const { firstPage } = usePagination();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);

    return <PatientDocumentTable patient={patient} documents={documents} />;
};
