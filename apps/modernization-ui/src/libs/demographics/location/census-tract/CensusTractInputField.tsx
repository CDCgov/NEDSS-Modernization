import { onlyNumericKeys } from 'design-system/input/numeric';
import { MaskedTextInputField, MaskedTextInputFieldProps } from 'design-system/input/text';

type CensusTractInputFieldProps = Omit<MaskedTextInputFieldProps, 'mask' | 'pattern' | 'type' | 'inputMode'>;

const CensusTractInputField = (props: CensusTractInputFieldProps) => (
    <MaskedTextInputField
        mask="____.__"
        pattern="^(?!0000)(\d{4})(?:\.(?!00|99)\d{2})?$"
        onKeyDown={onlyNumericKeys}
        {...props}
    />
);

export { CensusTractInputField };
