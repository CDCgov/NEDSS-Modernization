/* eslint-disable no-redeclare */
import { parseISO } from 'date-fns';

function internalizeDateTime(input: string): string;
function internalizeDateTime(input: Date): string;
function internalizeDateTime(input: string | null | undefined): null;
function internalizeDateTime(input: Date | null | undefined): null;
function internalizeDateTime(input: string | Date | null | undefined) {
    if (typeof input === 'string') {
        return internalizeDateTime(parseISO(input));
    } else if (input instanceof Date) {
        return input.toLocaleDateString('en-US', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: 'numeric',
            minute: 'numeric',
            hour12: true
        });
    }

    return null;
}

export { internalizeDateTime };
