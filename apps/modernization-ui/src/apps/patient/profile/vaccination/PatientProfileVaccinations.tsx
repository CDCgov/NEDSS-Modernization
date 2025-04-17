import { PaginationProvider } from 'pagination';
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
            <PaginationProvider pageSize={pageSize}>
                <PatientVaccinationContainer
                    source={usePatientProfileVaccinationAPI}
                    patient={patient}
                    allowAdd={allowAdd}
                />
            </PaginationProvider>
        </div>
    );
};
