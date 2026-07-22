import { useId, useEffect } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { NumericInput, NumericRangeInput, TextInputField } from 'design-system/input';
import { DatePickerInput } from 'design-system/date';
import { DatePickerRange } from 'design-system/date/range/DatePickerRange.tsx';
import { DateBetweenCriteria } from 'design-system/date/criteria';
import { NumberBetweenCriteria } from 'design-system/input/range/NumberRangeField.tsx';
import { BETWEEN_OPERATOR } from './operators.ts';
import { ReactComponentLike } from 'prop-types';
import { SIZING } from 'apps/report/constants.ts';

const RANGE_COMPONENTS: Record<string, ReactComponentLike> = {
    date: DatePickerRange,
    number: NumericRangeInput,
} as const;

const SINGLE_COMPONENTS: Record<string, ReactComponentLike> = {
    date: DatePickerInput,
    number: NumericInput,
    text: TextInputField,
} as const;

const BETWEEN_OPERATOR_NAME = BETWEEN_OPERATOR.name;

const getConvertedRange = (props: ValueEditorProps<FullField>): DateBetweenCriteria | NumberBetweenCriteria => {
    if (props.operator === BETWEEN_OPERATOR_NAME && typeof props.value === 'string' && props.value) {
        const [from = '', to = ''] = props.value.split(',');
        return { between: { from, to } };
    }
    return { between: { from: '', to: '' } };
};

const ValueInput = (props: ValueEditorProps<FullField>) => {
    const id = useId();
    const { handleOnChange, inputType, operator, title, value } = props;
    const labelName = title ?? '';
    const isBetween = operator === BETWEEN_OPERATOR_NAME;
    const InputComponent = isBetween ? RANGE_COMPONENTS[inputType!] : SINGLE_COMPONENTS[inputType!];

    let convertedValue = isBetween ? getConvertedRange(props) : (value ?? '');

    // eslint-disable-next-line max-len
    // adapted from https://github.com/mcmcgrath13/react-querybuilder/blob/87a991b124fa9060431ac8e1e8f42b789a5ddecb/packages/react-querybuilder/src/components/ValueEditor.tsx#L312-L322
    useEffect(() => {
        // clear input when changing from between to other operators
        if (BETWEEN_OPERATOR_NAME !== operator && value.includes(',')) {
            handleOnChange('');
        }
    }, [handleOnChange, operator, value]);

    const handleSingleOnChange = (newValue: number | string | undefined) => {
        if (newValue !== undefined) {
            props.handleOnChange(newValue.toString());
        } else {
            props.handleOnChange('');
        }
    };

    const handleBetweenOnChange = (incoming: DateBetweenCriteria | NumberBetweenCriteria) => {
        if (!incoming) {
            props.handleOnChange('');
            return;
        }
        const nextFrom = incoming.between.from ?? '';
        const nextTo = incoming.between.to ?? '';

        props.handleOnChange(`${nextFrom},${nextTo}`);
    };

    return (
        <div className={'queryBuilder-value'}>
            <InputComponent
                id={id}
                label={isBetween ? '' : labelName}
                value={convertedValue}
                name={labelName}
                onChange={isBetween ? handleBetweenOnChange : handleSingleOnChange}
                required
                sizing={SIZING}
            />
        </div>
    );
};

export { ValueInput };
