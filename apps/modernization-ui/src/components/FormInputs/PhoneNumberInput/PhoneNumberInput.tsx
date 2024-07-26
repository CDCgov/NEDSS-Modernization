import { Orientation, Sizing } from 'components/Entry';
import { Input } from 'components/FormInputs/Input';

type PhoneNumberInputProps = {
    defaultValue?: string | null;
    label?: string;
    error?: string;
    mask?: string;
    pattern?: string;
    orientation?: Orientation;
    sizing?: Sizing;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'type'>;
export const PhoneNumberInput = ({ ...props }: PhoneNumberInputProps) => <Input type="tel" {...props} />;
