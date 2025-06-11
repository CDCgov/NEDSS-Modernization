import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { NameDemographic } from 'libs/patient/demographics/name';
import { useEffect } from 'react';

type Request = { patient: number };

type Interaction = {
    isLoading: boolean;
    data: NameDemographic[];
};

const resolver = (request: Request) => PatientFileService.names(request);

const usePatientFileNames = (patient: number): Interaction => {
    const interaction = useApi({ resolver });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data, isLoading: interaction.isLoading };
};

export { usePatientFileNames };
