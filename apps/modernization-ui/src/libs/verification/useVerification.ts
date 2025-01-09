import { useCallback, useEffect, useState } from 'react';
import { Control, FieldPath, FieldPathValue, FieldValues, useWatch } from 'react-hook-form';
import { Validator } from 'validation';

type Violation<V> = {
    value: V;
    message: string | undefined;
};

type VerificationOptions<
    Values extends FieldValues = FieldValues,
    Name extends FieldPath<Values> = FieldPath<Values>
> = {
    name: Name;
    control: Control<Values>;
    constraint: Validator<FieldPathValue<Values, Name>>;
};

type VerificationInteraction<Value> = {
    verify: () => void;
    violation?: Violation<Value>;
};

/**
 * Applies a constraint to a value providing any violations if the constraint fails.
 *
 * @param {VerificationOptions} param0 The options for the verification, including
 *  - the name of the field that is being verified.
 *  - the control of the form that is being verified.
 *  - the constraint to apply for verification.
 * @return {VerificationInteraction} The interactions with the hook providing the verify function and any violations.
 */
const useVerification = <V extends FieldValues = FieldValues, N extends FieldPath<V> = FieldPath<V>>({
    name,
    control,
    constraint
}: VerificationOptions<V, N>): VerificationInteraction<V> => {
    const [violation, setViolation] = useState<Violation<FieldPathValue<V, N>>>();

    const current = useWatch({ control, name });

    useEffect(() => {
        setViolation((existing) => (current !== existing?.value ? undefined : existing));
    }, [current, setViolation]);

    const verify = useCallback(() => {
        const result = constraint(current);

        if (typeof result === 'string') {
            setViolation({ value: current, message: result });
        } else {
            setViolation(undefined);
        }
    }, [setViolation, constraint, current]);

    return {
        verify,
        violation
    };
};

export { useVerification };
export type { VerificationOptions };
