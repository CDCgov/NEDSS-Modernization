import { internalizeDate } from 'date';

export const toDateDisplay = (dateString?: string): string => {
    return internalizeDate(dateString) ?? '---';
};
