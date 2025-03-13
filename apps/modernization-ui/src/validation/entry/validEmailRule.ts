import { maxLengthRule } from './maxLengthRule';

const validEmailRule = (maxLength: number = 100) => ({
    pattern: {
        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
        message: 'Please enter a valid email address (example: youremail@website.com).'
    },
    ...maxLengthRule(maxLength)
});

export { validEmailRule };
