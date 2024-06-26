import { maxLengthRule } from './maxLengthRule';

const dataMartNameRule = {
    pattern: {
        value: /^[A-Za-z_][A-Za-z0-9_]*$/,
        message: 'Name cannot start with a number and can only contain alphanumeric and underscore characters.'
    },
    ...maxLengthRule(21)
};

export { dataMartNameRule };
