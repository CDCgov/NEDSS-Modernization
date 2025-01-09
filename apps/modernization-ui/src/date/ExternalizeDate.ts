/* eslint-disable no-redeclare */
import { formatISOWithOptions, parse } from 'date-fns/fp';
import { INTERNAL_DATE_FORMAT } from './Dates';

const externalized = formatISOWithOptions({ representation: 'date' });
const asDate = parse(new Date(), INTERNAL_DATE_FORMAT);

function externalizeDate(input: string): string;
function externalizeDate(input: Date): string;
function externalizeDate(input: string | null | undefined): null;
function externalizeDate(input: Date | null | undefined): null;
function externalizeDate(input: string | Date | null | undefined) {
    if (input) {
        if (typeof input === 'string') {
            return externalized(asDate(input));
        } else if (input instanceof Date) {
            return externalized(input);
        }
    }

    return null;
}

export { externalizeDate, asDate };
