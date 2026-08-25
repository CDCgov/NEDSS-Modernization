import { useEffect, useId } from 'react';

import { ReactComponentLike } from 'prop-types';
import { FullField, FullOperator, ValueEditorProps } from 'react-querybuilder';

import { SIZING } from 'apps/report/constants.ts';
import { DatePickerInput } from 'design-system/date';
import { DateBetweenCriteria } from 'design-system/date/criteria';
import { DatePickerRange } from 'design-system/date/range/DatePickerRange.tsx';
import { NumericInput, NumericRangeInput, TextInputField } from 'design-system/input';
import {
    initialNumberBetweenCriteria,
    NumberBetweenCriteria,
} from 'design-system/input/numeric/range/NumberRangeField.tsx';

import { ValueSetMetadata } from './AdvancedFilter.tsx';
import { BETWEEN_OPERATOR } from './operators.ts';

const RANGE_COMPONENTS: Record<string, ReactComponentLike> = {
    date: DatePickerRange,
    number: NumericRangeInput,
} as const;

const SINGLE_COMPONENTS: Record<string, ReactComponentLike> = {
    date: DatePickerInput,
    number: NumericInput,
    text: TextInputField,
} as const;

const getConvertedDateRange = (value: unknown): DateBetweenCriteria => {
    if (typeof value === 'string' && value && value.includes(',')) {
        const [from, to] = value.split(',');
        return { between: { from, to } };
    }
    return { between: { from: '', to: '' } };
};

const getConvertedNumberRange = (value: unknown): NumberBetweenCriteria => {
    if (typeof value === 'string' && value && value.includes(',')) {
        const [from, to] = value
            .split(',')
            .map((v) => Number.parseInt(v))
            .map((v) => (Number.isNaN(v) ? null : v));
        return { between: { from, to } };
    }
    return initialNumberBetweenCriteria;
};

const ValueInput = (props: ValueEditorProps<ValueSetMetadata & FullField & FullOperator>) => {
    const id = useId();
    const { handleOnChange, inputType, operator, title, value } = props;
    const labelName = title ?? '';
    const isBetween = operator === BETWEEN_OPERATOR.name;
    const InputComponent = isBetween ? RANGE_COMPONENTS[inputType!] : SINGLE_COMPONENTS[inputType!];

    const convertedValue = isBetween
        ? inputType === 'number'
            ? getConvertedNumberRange(value)
            : getConvertedDateRange(value)
        : (value ?? '');

    // eslint-disable-next-line max-len
    // adapted from https://github.com/mcmcgrath13/react-querybuilder/blob/87a991b124fa9060431ac8e1e8f42b789a5ddecb/packages/react-querybuilder/src/components/ValueEditor.tsx#L312-L322
    useEffect(() => {
        // clear input when changing from between to other operators
        if (!isBetween && inputType !== 'text' && value.includes(',')) {
            handleOnChange('');
        }
    }, [handleOnChange, operator, value, inputType]);

    const handleSingleOnChange = (newValue: unknown) => {
        props.handleOnChange(newValue?.toString() ?? '');
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
        <div className="queryBuilder-value">
            <InputComponent
                id={id}
                label={isBetween ? '' : labelName}
                value={convertedValue}
                name={labelName}
                onChange={isBetween ? handleBetweenOnChange : handleSingleOnChange}
                required={true}
                sizing={SIZING}
            />
        </div>
    );
};

export { ValueInput };
