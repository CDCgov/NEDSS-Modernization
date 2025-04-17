import { PaginationProvider } from 'pagination';

import { usePatientProfileDocumentsAPI } from './usePatientProfileDocumentsAPI';
import { PatientDocumentContainer } from './PatientDocumentContainer';

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientProfileDocuments = ({ patient, pageSize }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PaginationProvider pageSize={pageSize}>
                <PatientDocumentContainer source={usePatientProfileDocumentsAPI} patient={patient} />
            </PaginationProvider>
        </div>
    );
};
