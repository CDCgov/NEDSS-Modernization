import { MaskedTextInputFieldProps, MaskedTextInputField } from 'design-system/input/text';
import { onlyNumericKeys } from 'design-system/input/numeric';

type PhoneNumberInputFieldProps = Omit<
    MaskedTextInputFieldProps,
    'mask' | 'pattern' | 'type' | 'inputMode' | 'onKeyDown'
>;

const PhoneNumberInputField = (props: PhoneNumberInputFieldProps) => (
    <MaskedTextInputField
        type="tel"
        mask="___-___-____"
        pattern="^\d{3}-\d{3}-\d{4}$"
        onKeyDown={onlyNumericKeys}
        {...props}
    />
);

export { PhoneNumberInputField };
