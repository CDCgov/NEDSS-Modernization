import { Administrative, PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { useEffect } from 'react';

const all = (request: Request) => PatientFileService.administrative(request).then((response) => response);

type Request = { patient: number };

type Interaction = {
    data?: Administrative;
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
