import { validateDateEntry } from 'design-system/date';
import { DateCriteria, isDateBetweenCriteria, isDateEqualsCriteria } from './dateCriteria';
import { validateDateRange } from './range/validateDateRange';

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
