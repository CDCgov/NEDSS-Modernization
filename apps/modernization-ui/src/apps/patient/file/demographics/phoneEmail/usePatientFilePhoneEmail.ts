import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';
import { useEffect } from 'react';

type Request = { patient: number };

type Interaction = {
    isLoading: boolean;
    data: PhoneEmailDemographic[];
};

const resolver = (request: Request) => PatientFileService.phones(request);

const usePatientFilePhoneEmail = (patient: number): Interaction => {
    const interaction = useApi({ resolver });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data ?? [];

    return { data, isLoading: interaction.isLoading };
};

export { usePatientFilePhoneEmail };
