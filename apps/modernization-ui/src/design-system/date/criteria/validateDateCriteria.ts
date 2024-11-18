import { DateCriteria, isDateEqualsCriteria } from '../entry';
import { validateDateEntry } from '../validateDateEntry';

const validateDateCriteria =
    (name: string) =>
    (value: DateCriteria): boolean | string => {
        if (isDateEqualsCriteria(value)) {
            return validateDateEntry(name)(value?.equals);
        }
        // TODO: validate between
        return true;
    };

export { validateDateCriteria };
