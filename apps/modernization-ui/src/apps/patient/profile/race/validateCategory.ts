import { ApiError } from 'generated';
import { PatientRaceService } from 'generated/services/PatientRaceService';

const checkForExistingCategory = (patient: number, category: string) =>
    PatientRaceService.validateCategory({ category, patient: patient })
        .catch((error: ApiError) =>
            error.body?.description
                ? `The patient has an existing race demographic for "${error.body?.description}"`
                : false
        )
        .then((response) => response ?? true);

const validateCategory =
    (patient: number, allowed?: string) =>
    (category: string): Promise<boolean | string> =>
        category === allowed ? Promise.resolve(true) : checkForExistingCategory(patient, category);
export { validateCategory };
