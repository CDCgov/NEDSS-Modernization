import { FieldPath, FieldValues } from 'react-hook-form';
import { useVerification, VerificationOptions } from './useVerification';

type VerificationRenderProps = {
    violation?: string;
    verify: () => void;
};

type VerificationProps<
    Values extends FieldValues = FieldValues,
    Name extends FieldPath<Values> = FieldPath<Values>
> = VerificationOptions<Values, Name> & {
    render: (result: VerificationRenderProps) => JSX.Element;
};

const Verification = <V extends FieldValues = FieldValues, N extends FieldPath<V> = FieldPath<V>>({
    name,
    control,
    constraint,
    render
}: VerificationProps<V, N>) => {
    const { violation, verify } = useVerification({ name, control, constraint });

    return render({ verify, violation: violation?.message });
};

export { Verification };
