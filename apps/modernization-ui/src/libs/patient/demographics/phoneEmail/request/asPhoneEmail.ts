import { asValue } from 'options';
import { exists, orUndefined } from 'utils';
import { PhoneEmailDemographic } from '../phoneEmails';
import { PhoneEmailDemographicRequest } from './phoneEmailRequest';

const asPhoneEmail = (demographic: Partial<PhoneEmailDemographic>): PhoneEmailDemographicRequest | undefined => {
    const { asOf, type, use, countryCode, phoneNumber, extension, email, url, comment } = demographic;

    if (asOf && exists(type) && exists(use)) {
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
