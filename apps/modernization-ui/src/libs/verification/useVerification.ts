import { useCallback, useState } from 'react';
import { validateIfPresent, Validator } from 'validation';

type VerificationOptions<T> = {
    constraint: Validator<T>;
};

type VerificationInteraction<T> = {
    verify: (value?: T) => void;
    violation?: string;
};

/**
 * Applies a constraint to a value and provides any violations if the constraint fails.
 *
 * @param {VerificationOptions} param0 The options for the verification, including the constraint to apply for verification.
 * @return {VerificationInteraction} The interactions with the hook providing the verify function and any violations.
 */
const useVerification = <V>({ constraint }: VerificationOptions<V>): VerificationInteraction<V> => {
    const [violation, setViolation] = useState<string | undefined>();

    const verify = useCallback(
        (value?: V) => {
            const result = validateIfPresent(constraint)(value);

            if (typeof result === 'string') {
                setViolation(result);
            } else {
                setViolation(undefined);
            }
        },
        [setViolation, constraint]
    );

    return {
        verify,
        violation
    };
};

export { useVerification };
