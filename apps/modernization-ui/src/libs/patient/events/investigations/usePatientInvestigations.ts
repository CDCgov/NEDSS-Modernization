import { useApi } from 'libs/api';
import { PatientFileService } from 'generated';
import { PatientInvestigation } from './PatientInvestigation';
import { transformer } from './transformer';
import { useEffect } from 'react';

const all = (request: Request) =>
    PatientFileService.investigations(request).then((response) => (response ? response.map(transformer) : []));

type Request = { patientId: number };

type Interaction = {
    data: PatientInvestigation[];
};

const usePatientInvestigations = (patient?: number): Interaction => {
    const interaction = useApi({ resolver: all });

    useEffect(() => {
        if (patient) {
            interaction.load({ patientId: patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data };
};

export { usePatientInvestigations };
