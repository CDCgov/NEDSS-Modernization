import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { AdministrativeInformation } from 'libs/patient/demographics/administrative/AdministrativeInformation';
import { useEffect } from 'react';

const all = (request: Request) => PatientFileService.administrative(request).then((response) => response);

type Request = { patient: number };

type Interaction = {
    data?: AdministrativeInformation;
};

const useAdministrativeInformation = (patient?: number): Interaction => {
    const interaction = useApi({ resolver: all });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient: patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data;

    return { data };
};

export { useAdministrativeInformation };
