import { NewPatientEntry } from 'apps/patient/add';

export function isMissingFields(entry: NewPatientEntry) {
    for (const key in entry) {
        if (Object.hasOwnProperty.call(entry, key)) {
            const value = entry[key as keyof NewPatientEntry];
            if (
                value === null ||
                value === undefined ||
                (typeof value === 'string' && value === '') ||
                (Array.isArray(value) && value.length === 0)
            ) {
                return true; // At least one property has no value
            }
        }
    }
    return false;
}
