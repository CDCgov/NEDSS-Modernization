import { FindVaccinationsForPatientQuery } from 'generated/graphql/schema';
import { mapNonNull } from 'utils/mapNonNull';
import { asLocalDate } from 'date';
import { Vaccination } from './PatientVaccination';
import { orNull } from 'utils/orNull';

type Result = FindVaccinationsForPatientQuery['findVaccinationsForPatient'];

type AssociatedWith = {
    __typename?: 'PatientVaccinationInvestigation';
    id: string;
    local: string;
    condition: string;
};

type Content = {
    __typename?: 'PatientVaccination';
    vaccination: string;
    createdOn: any;
    provider?: string | null;
    administeredOn?: any | null;
    administered: string;
    event: string;
    associatedWith?: AssociatedWith | null;
};

const internalized = (content: Content): Vaccination | null =>
    content && {
        ...content,
        createdOn: new Date(content.createdOn),
        administeredOn: (content.administeredOn && asLocalDate(content.administeredOn)) || null,
        provider: orNull(content.provider)
    };

export const transform = (result: Result): Vaccination[] => mapNonNull(internalized, result?.content);
