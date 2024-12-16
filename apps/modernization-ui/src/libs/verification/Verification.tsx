import { Validator } from 'validation';
import { useVerification } from './useVerification';

type VerificationRenderProps<T> = {
    violation?: string;
    verify: (value?: T) => void;
};

type VerificationProps<T> = {
    constraint: Validator<T>;
    render: (result: VerificationRenderProps<T>) => JSX.Element;
};

const Verification = <T,>({ constraint, render }: VerificationProps<T>) => {
    const { violation, verify } = useVerification({ constraint });

    return render({ violation, verify });
};

export { Verification };
