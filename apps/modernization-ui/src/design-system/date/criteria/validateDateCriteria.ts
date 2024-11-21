import { DateCriteria, isDateBetweenCriteria, isDateEqualsCriteria } from '../entry';
import { validateDateEntry } from '../validateDateEntry';
import { validateDateRange } from '../validateDateRange';

const validateDateCriteria =
    (name: string) =>
    (value: DateCriteria): boolean | string => {
        if (isDateEqualsCriteria(value)) {
            return validateDateEntry(name)(value?.equals);
        }
        if (isDateBetweenCriteria(value)) {
            return validateDateRange(name)(value);
        }
        return true;
    };

export { validateDateCriteria };
