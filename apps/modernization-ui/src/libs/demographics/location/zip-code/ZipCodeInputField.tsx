import { MaskedTextInputFieldProps, MaskedTextInputField } from 'design-system/input/text';
import { onlyNumericKeys } from 'design-system/input/numeric';

type ZipCodeInputFieldProps = Omit<MaskedTextInputFieldProps, 'mask' | 'pattern' | 'type' | 'inputMode' | 'onKeyDown'>;

const ZipCodeInputField = (props: ZipCodeInputFieldProps) => (
    <MaskedTextInputField mask="_____-____" pattern="^\d{5}(?:[\-\s]\d{4})?$" onKeyDown={onlyNumericKeys} {...props} />
);

export { ZipCodeInputField };
