import { internalizeDate } from 'date';
import { isAfter } from 'date-fns';

const isBefore =
    (max: string) =>
    (current: string): string | undefined => {
        if (max && current) {
            const maxDate = new Date(max);
            const currentDate = new Date(current);

            if (isAfter(currentDate, maxDate)) {
                return `Date cannot be after: ${internalizeDate(maxDate)}`;
            }
        }
    };

export { isBefore };
