import { PatientProfileService } from 'generated';
import { Creator } from './extended/useAddExtendedPatient';
import { NewPatient } from './api';

const creator: Creator = (input: NewPatient) => PatientProfileService.create({ requestBody: input });

export { creator };
