import parse from 'date-fns/parse';
import { INTERNAL_DATE_FORMAT } from './Dates';

export const asExternalDate = (input: string | Date | null) => {
    if (input) {
        if (typeof input === 'string') {
            return parse(input, INTERNAL_DATE_FORMAT, new Date()).toISOString();
        } else if (input instanceof Date) {
            return input.toISOString();
        }
    }

    return null;
};
