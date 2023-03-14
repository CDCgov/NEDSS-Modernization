import { formatISOWithOptions, parse } from 'date-fns/fp';
import { INTERNAL_DATE_FORMAT } from './Dates';

const externalized = formatISOWithOptions({ representation: 'date' });
const asDate = parse(new Date(), INTERNAL_DATE_FORMAT);

export const externalizeDate = (input: string | Date | null) => {
    if (input) {
        if (typeof input === 'string') {
            return externalized(asDate(input));
        } else if (input instanceof Date) {
            return externalized(input);
        }
    }

    return null;
};
