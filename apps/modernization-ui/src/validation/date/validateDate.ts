import { asDate } from 'date';
import { isFuture } from 'date-fns';

const validateDate = (value: string): boolean | string => {
    const check = asDate(value);
    if (isFuture(check)) {
        return `${value} occurs after today.`;
    }

    return true;
};

export { validateDate };
