import { PageProvider } from 'page';
import { PatientVaccinationContainer } from './PatientVaccinationContainer';
import { usePatientProfileVaccinationAPI } from './usePatientProfileVaccinationAPI';

type Props = {
    patient?: string;
    pageSize: number;
    allowAdd?: boolean;
};

export const PatientProfileVaccinations = ({ patient, pageSize, allowAdd = false }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PageProvider pageSize={pageSize}>
                <PatientVaccinationContainer
                    source={usePatientProfileVaccinationAPI}
                    patient={patient}
                    allowAdd={allowAdd}
                />
            </PageProvider>
        </div>
    );
};
