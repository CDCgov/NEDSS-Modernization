/* eslint-disable no-redeclare */
import { parseISO } from 'date-fns';

function internalizeDate(input: string): string;
function internalizeDate(input: Date): string;
function internalizeDate(input: string | null | undefined): null;
function internalizeDate(input: Date | null | undefined): null;
function internalizeDate(input: string | Date | null | undefined) {
    if (typeof input === 'string') {
        return internalizeDate(parseISO(input));
    } else if (input instanceof Date) {
        return input.toLocaleDateString('en-US', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    }

    return null;
}

export { internalizeDate };
