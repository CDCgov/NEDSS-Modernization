import { useApi } from 'libs/api';
import { PatientFileService } from 'generated';
import { PatientInvestigation } from './PatientInvestigation';
import { transformer } from './transformer';
import { useEffect } from 'react';

type Request = { patientId: number };

const open = (request: Request) =>
    PatientFileService.openInvestigations(request).then((response) => (response ? response.map(transformer) : []));

type Interaction = {
    data: PatientInvestigation[];
};

const usePatientOpenInvestigations = (patient?: number): Interaction => {
    const interaction = useApi({ resolver: open });

    useEffect(() => {
        if (patient) {
            interaction.load({ patientId: patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data };
};
export { usePatientOpenInvestigations };
