import { maxLengthRule } from './maxLengthRule';

const message = 'Please enter a valid email address (example: youremail@website.com)';
const validEmailRule = (maxLength: number = 100, name?: string) => ({
    pattern: {
        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
        message: name ? `The ${name} ${message}` : message
    },
    ...maxLengthRule(maxLength)
});

export { validEmailRule };
