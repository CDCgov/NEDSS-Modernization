import { PageProvider } from 'page';

import { usePatientProfileDocumentsAPI } from './usePatientProfileDocumentsAPI';
import { PatientDocumentContainer } from './PatientDocumentContainer';

type Props = {
    patient?: string;
    nbsBase: string;
    pageSize: number;
};

export const PatientProfileDocuments = ({ patient, nbsBase, pageSize }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PageProvider pageSize={pageSize}>
                <PatientDocumentContainer source={usePatientProfileDocumentsAPI} nbsBase={nbsBase} patient={patient} />
            </PageProvider>
        </div>
    );
};
