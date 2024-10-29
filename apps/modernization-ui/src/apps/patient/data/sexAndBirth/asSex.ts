import { asValue } from 'options';
import { Sex } from '../api';
import { SexEntry } from '../entry';
import { isEmpty, orUndefined } from 'utils';

const asSex = (entry: SexEntry): Sex | undefined => {
    const { asOf, ...remaining } = entry;

    const { current, unknownReason, transgenderInformation, additionalGender } = remaining;

    if (!isEmpty(remaining)) {
        return {
            asOf,
            current: asValue(current),
            unknownReason: asValue(unknownReason),
            transgenderInformation: asValue(transgenderInformation),
            additionalGender: orUndefined(additionalGender)
        };
    }
};

export { asSex };
