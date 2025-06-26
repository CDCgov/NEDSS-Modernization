import { useEffect } from 'react';
import { useApi } from 'libs/api';
import { PatientLabReport } from './patientLabReport';
import { loader } from './loader';

type Interaction = {
    data: PatientLabReport[];
};

const usePatientLabReports = (patient?: number): Interaction => {
    const interaction = useApi({ resolver: loader });

    useEffect(() => {
        if (patient) {
            interaction.load(patient);
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data };
};

export { usePatientLabReports };
