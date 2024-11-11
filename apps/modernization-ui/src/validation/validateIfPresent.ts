import { Validator } from './validation';

const validateIfPresent =
    <I>(validator: Validator<I>) =>
    (value?: I): boolean | string =>
        value ? validator(value) : true;

export { validateIfPresent };
