import { parse } from 'date-fns/fp';
import { INTERNAL_DATE_FORMAT } from './Dates';

const externalized = (input: Date) => input.toISOString();

export const externalizeDateTime = (input: string | Date | null) => {
    if (input) {
        if (typeof input === 'string') {
            return externalized(parse(new Date(), INTERNAL_DATE_FORMAT)(input));
        } else if (input instanceof Date) {
            return externalized(input);
        }
    }

    return null;
};
