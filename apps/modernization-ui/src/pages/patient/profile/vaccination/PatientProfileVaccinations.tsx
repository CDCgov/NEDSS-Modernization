import { PageProvider } from 'page';
import { PatientVaccinationContainer } from './PatientVaccinationContainer';
import { usePatientProfileVaccinationAPI } from './usePatientProfileVaccinationAPI';

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientProfileVaccinations = ({ patient, pageSize }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PageProvider pageSize={pageSize}>
                <PatientVaccinationContainer source={usePatientProfileVaccinationAPI} patient={patient} />
            </PageProvider>
        </div>
    );
};
