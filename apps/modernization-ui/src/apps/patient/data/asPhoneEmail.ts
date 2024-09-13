import { asValue } from 'options';
import { PhoneEmail } from './api';
import { PhoneEmailEntry } from './entry';

const asPhoneEmail = (entry: PhoneEmailEntry): PhoneEmail => {
    const { type, use, ...remaining } = entry;

    return {
        type: asValue(type),
        use: asValue(use),
        ...remaining
    };
};

export { asPhoneEmail };
