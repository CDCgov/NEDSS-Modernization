import { PatientEthnicityDemographic, PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { useEffect } from 'react';

const all = (request: Request) => PatientFileService.ethnicityDemographics(request).then((response) => response);

type Request = { patient: number };

type Interaction = {
    data?: PatientEthnicityDemographic;
};

const useEthnicity = (patient?: number): Interaction => {
    const interaction = useApi({ resolver: all });

    useEffect(() => {
        if (patient) {
            interaction.load({ patient: patient });
        }
    }, [patient, interaction.load]);

    const data = interaction.data;

    return { data };
};

export { useEthnicity };
