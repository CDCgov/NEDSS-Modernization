import { asValue } from 'options';
import { PhoneEmail } from '../api';
import { PhoneEmailEntry } from './entry';
import { exists, orUndefined } from 'utils';

const asPhoneEmail = (entry: PhoneEmailEntry): PhoneEmail | undefined => {
    const { asOf, type, use, countryCode, phoneNumber, extension, email, url, comment } = entry;

    if (exists(type) && exists(use)) {
        return {
            asOf,
            type: asValue(type),
            use: asValue(use),
            countryCode: orUndefined(countryCode),
            phoneNumber: orUndefined(phoneNumber),
            extension: orUndefined(extension),
            email: orUndefined(email),
            url: orUndefined(url),
            comment: orUndefined(comment)
        };
    }
};

export { asPhoneEmail };
