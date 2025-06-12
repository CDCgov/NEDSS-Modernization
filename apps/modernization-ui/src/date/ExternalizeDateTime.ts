import { parse } from 'date-fns/fp';
import { INTERNAL_DATE_FORMAT } from './Dates';

const externalized = (input: Date) => input.toISOString();

export const externalizeDateTime = (input: string | Date | null | undefined) => {
    if (input) {
        if (typeof input === 'string') {
            const asDate = parse(input ? new Date(input) : new Date(), INTERNAL_DATE_FORMAT);
            return externalized(asDate(input));
        } else if (input instanceof Date) {
            return externalized(input);
        }
    }

    return null;
};
