import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { INTERNAL_DATE_FORMAT } from './Dates';

export const asInternalDate = (input: string | Date | null) => {
    if (input) {
        if (typeof input === 'string') {
            return format(parseISO(input), INTERNAL_DATE_FORMAT);
        } else if (input instanceof Date) {
            return format(input, INTERNAL_DATE_FORMAT);
        }
    }

    return null;
};
