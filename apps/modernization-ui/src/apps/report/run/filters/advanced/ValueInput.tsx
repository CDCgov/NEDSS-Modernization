import React, { useId } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { NumericInput, TextInputField } from '../../../../../design-system/input';
import { DatePickerInput } from '../../../../../design-system/date';
import { DatePickerRange } from '../../../../../design-system/date/range/DatePickerRange.tsx';
import { DateBetweenCriteria } from '../../../../../design-system/date/criteria';
import { NumberInputRange } from '../../../../../design-system/input/range/NumberInputRange.tsx';
import { NumberBetweenCriteria } from '../../../../../design-system/input/range/NumberRangeField.tsx';

const RANGE_COMPONENTS = {
    date: DatePickerRange,
    number: NumberInputRange,
} as const;

const SINGLE_COMPONENTS = {
    date: DatePickerInput,
    number: NumericInput,
    text: TextInputField,
} as const;

const getConvertedRange = (props): DateBetweenCriteria | NumberBetweenCriteria => {
    if (props.operator === 'between' && props.value) {
        const [from = '', to = ''] = props.value.split(',');
        return { between: { from, to } };
    }
    return { between: { from: '', to: '' } };
};

const ValueInput = (props: ValueEditorProps<FullField>) => {
    const id = useId();
    const labelName = props.title ?? '';
    const isBetween = props.operator === 'between';
    const InputComponent = isBetween ? RANGE_COMPONENTS[props.inputType] : SINGLE_COMPONENTS[props.inputType];

    const value = isBetween ? getConvertedRange(props) : (props.value ?? '');

    const handleSingleOnChange = (newValue: number | string | undefined) => {
        props.handleOnChange(newValue);
    };

    const handleBetweenOnChange = (incoming: DateBetweenCriteria | NumberBetweenCriteria | undefined) => {
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
                value={value}
                name={labelName}
                onChange={isBetween ? handleBetweenOnChange : handleSingleOnChange}
                required
            />
        </div>
    );
};

export { ValueInput };
