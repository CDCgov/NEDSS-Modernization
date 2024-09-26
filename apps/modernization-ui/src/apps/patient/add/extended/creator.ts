import { PatientProfileService } from 'generated';
import { Creator } from './useAddExtendedPatient';
import { NewPatient } from './api';

const creator: Creator = (input: NewPatient) => PatientProfileService.create({ requestBody: input });

export { creator };
