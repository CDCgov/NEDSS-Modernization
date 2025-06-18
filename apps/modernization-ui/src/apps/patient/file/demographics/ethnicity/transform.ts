import { PatientEthnicityDemographic } from 'generated';
import { Ethnicity } from 'libs/patient/demographics/ethnicity/Ethnicity';

const transformer = (value: PatientEthnicityDemographic): Ethnicity => ({
    ...value,
    detailed: value.detailed?.map((detail) => detail.name).join(', ')
});

export { transformer };
