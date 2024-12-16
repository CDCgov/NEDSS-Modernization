import { validateIfPresent } from 'validation';
import { maxLengthRule } from 'validation/entry';
import { validatePhoneNumber } from 'validation/phone';

const validPhoneNumberRule = (name: string) => ({
    validate: {
        properNumber: validateIfPresent((value: string) => validatePhoneNumber(value, name))
    },
    ...maxLengthRule(20, name)
});

export { validPhoneNumberRule };
