import { asValue } from 'options';
import { Sex } from '../api';
import { SexEntry } from '../entry';

const asSex = (entry: SexEntry): Sex => {
    const { current, unknownReason, transgenderInformation, ...remaining } = entry;

    return {
        current: asValue(current),
        unknownReason: asValue(unknownReason),
        transgenderInformation: asValue(transgenderInformation),
        ...remaining
    };
};

export { asSex };
