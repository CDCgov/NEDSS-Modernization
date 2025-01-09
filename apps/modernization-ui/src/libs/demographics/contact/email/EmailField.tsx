import { TextInputFieldProps, TextInputField } from 'design-system/input/text';

type PhoneNumberInputFieldProps = Omit<TextInputFieldProps, 'type' | 'inputMode' | 'onKeyDown'>;

const EmailField = (props: PhoneNumberInputFieldProps) => <TextInputField type="email" {...props} />;

export { EmailField };
