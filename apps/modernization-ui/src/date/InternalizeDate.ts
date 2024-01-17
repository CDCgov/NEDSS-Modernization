/* eslint-disable no-redeclare */
import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { INTERNAL_DATE_FORMAT } from './Dates';

function internalizeDate(input: string): string;
function internalizeDate(input: Date): string;
function internalizeDate(input: string | null | undefined): null;
function internalizeDate(input: Date | null | undefined): null;
function internalizeDate(input: string | Date | null | undefined) {
    if (input) {
        if (typeof input === 'string') {
            return format(parseISO(input), INTERNAL_DATE_FORMAT);
        } else if (input instanceof Date) {
            return format(input, INTERNAL_DATE_FORMAT);
        }
    }

    return null;
}

export { internalizeDate };
