import { PatientFileService } from 'generated';
import { useApi } from 'libs/api';
import { useEffect } from 'react';
import { transformer } from './transform';
import { Ethnicity } from 'libs/patient/demographics/ethnicity/Ethnicity';

const all = (request: Request) =>
    PatientFileService.ethnicityDemographics(request).then((response) => transformer(response));

type Request = { patient: number };

type Interaction = {
    data?: Ethnicity;
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
