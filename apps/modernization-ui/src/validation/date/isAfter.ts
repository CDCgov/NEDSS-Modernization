import { isBefore } from 'date-fns';

const isAfter =
    (max?: string) =>
    (current?: string): string | undefined => {
        if (max && current) {
            const maxDate = new Date(max);
            const currentDate = new Date(current);

            if (isBefore(currentDate, maxDate)) {
                return `Date cannot be before: ${max}`;
            }
        }
    };

export { isAfter };
