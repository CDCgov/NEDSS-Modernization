import { PatientProfileService } from 'generated';

import { NewPatient } from './api';
import { Creator } from './extended/useAddExtendedPatient';

const creator: Creator = (input: NewPatient) => PatientProfileService.create({ requestBody: input });

export { creator };
