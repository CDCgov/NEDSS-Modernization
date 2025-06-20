import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { useEffect } from 'react';

const all = (request: Request) => PatientFileService.administrative(request).then((response) => response);

type Request = { patient: number };

const useAdministrativeInformation = (patient?: number) => {
    const interaction = useApi({ resolver: all });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient: patient });
        }
    }, [patient, interaction.load]);

    return interaction;
};

export { useAdministrativeInformation };
