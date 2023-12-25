import { useEffect } from 'react';
import { usePage } from 'page';
import { Document } from './PatientDocuments';
import { PatientDocumentTable } from './PatientDocumentTable';

type Props = {
    source: (patient?: string) => Document[];
    patient?: string;
};

export const PatientDocumentContainer = ({ source, patient }: Props) => {
    const documents = source(patient);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);

    return <PatientDocumentTable patient={patient} documents={documents} />;
};
