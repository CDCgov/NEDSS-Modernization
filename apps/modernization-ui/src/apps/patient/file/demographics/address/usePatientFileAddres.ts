import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { AddressDemographic } from 'libs/patient/demographics/address';
import { useEffect } from 'react';

type Request = { patient: number };

type Interaction = {
    isLoading: boolean;
    data: AddressDemographic[];
};

const resolver = (request: Request) => PatientFileService.addresses(request);

const usePatientFileAddress = (patient: number): Interaction => {
    const interaction = useApi({ resolver });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data, isLoading: interaction.isLoading };
};

export { usePatientFileAddress };
