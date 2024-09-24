import { asValue } from 'options';
import { Sex } from '../api';
import { SexEntry } from '../entry';

const asSex = (entry: SexEntry): Sex => {
    const { asOf, current, unknownReason, transgenderInformation, additionalGender } = entry;

    return {
        asOf,
        current: asValue(current),
        unknownReason: asValue(unknownReason),
        transgenderInformation: asValue(transgenderInformation),
        additionalGender
    };
};

export { asSex };
