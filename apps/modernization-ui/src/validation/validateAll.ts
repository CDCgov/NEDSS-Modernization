import { ValidationResult, Validator } from './validation';

const validateAll =
    <I>(...validators: Validator<I>[]) =>
    (value: I): ValidationResult =>
        validators.reduce<ValidationResult>((previous, current) => {
            if (typeof previous === 'boolean' && previous) {
                const result = current(value);
                return result;
            }

            return previous;
        }, true);

export { validateAll };
