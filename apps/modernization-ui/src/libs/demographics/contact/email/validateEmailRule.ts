import { maxLengthRule } from 'validation/entry';
import { maybeValidateEmail } from './maybeValidateEmail';

const validEmailRule = (name: string) => ({
    validate: maybeValidateEmail(name),
    ...maxLengthRule(100, name)
});

export { validEmailRule };
