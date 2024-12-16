import { validateIfPresent } from 'validation';
import { maxLengthRule } from 'validation/entry';
import { validateEmail } from './validateEmail';

const validEmailRule = (name: string) => ({
    validate: validateIfPresent(validateEmail(name)),
    ...maxLengthRule(100, name)
});

export { validEmailRule };
