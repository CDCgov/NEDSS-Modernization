import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { IdentificationDemographic } from 'libs/patient/demographics/identifications/identifications';
import { useEffect } from 'react';

type Request = { patient: number };

type Interaction = {
    isLoading: boolean;
    data: IdentificationDemographic[];
};

const resolver = (request: Request) => PatientFileService.identifications(request);

const usePatientFileIdentification = (patient: number): Interaction => {
    const interaction = useApi({ resolver });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data, isLoading: interaction.isLoading };
};

export { usePatientFileIdentification };
