import React from 'react';
import './CreateQuestion.scss';
import { Radio } from '@trussworks/react-uswds';
import { Control, Controller } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';
import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    MessagingInfo,
    ReportingInfo
} from '../../generated';
import { QuestionFormType } from './CreateQuestion';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';

type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

type NumericQuestionProps = {
    control?: Control<CreateQuestionFormType, any>;
    isDisableUnitType: boolean;
    unitType?: string;
};
const unitTypeOption = [
    {
        name: 'Coded value',
        value: 'coded'
    },
    {
        name: 'Literal value',
        value: 'literal'
    }
];

export const CreateNumericQuestion = ({ control, isDisableUnitType, unitType }: NumericQuestionProps) => {
    let text = '';
    if (unitType) {
        text = unitType === 'coded' ? 'Related units valueset' : 'Related units literal';
    }
    return (
        <>
            <Controller
                control={control}
                name="minValue"
                rules={{
                    required: { value: true, message: 'Minimum Value required' },
                    maxLength: 50
                }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        className="field-space"
                        defaultValue={value?.toString()}
                        label="Minimum Value"
                        type="number"
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="maxValue"
                rules={{
                    required: { value: true, message: 'Maximum Value required' },
                    maxLength: 50
                }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        className="field-space"
                        defaultValue={value?.toString()}
                        label="Maximum Value"
                        type="number"
                        error={error?.message}
                        required
                    />
                )}
            />
            <label>
                Related units <span className="mandatory-indicator">*</span>
            </label>
            <Controller
                control={control}
                name="relatedUnits"
                defaultValue="No"
                render={({ field: { onChange, value } }) => (
                    <div className="radio-group">
                        <Radio
                            id="relatedUnits_Y"
                            name="relatedUnits"
                            value="Yes"
                            label="Yes"
                            onChange={onChange}
                            checked={value === 'Yes'}
                        />
                        <Radio
                            id="relatedUnits_N"
                            name="relatedUnits"
                            value="No"
                            label="No"
                            onChange={onChange}
                            checked={value === 'No'}
                        />
                    </div>
                )}
            />
            <Controller
                control={control}
                name="unitType"
                rules={{ required: { value: !isDisableUnitType, message: 'Unit type required' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        defaultValue={value}
                        label="Unit type"
                        disabled={isDisableUnitType}
                        onChange={onChange}
                        options={unitTypeOption}
                        error={error?.message}
                        required={!isDisableUnitType}
                    />
                )}
            />
            {!isDisableUnitType && unitType && (
                <Controller
                    control={control}
                    name={unitType === 'coded' ? 'relatedUnitsValueSet' : 'relatedUnitsLiteral'}
                    rules={{
                        required: { value: true, message: `${text} required` },
                        maxLength: 50
                    }}
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <Input
                            onChange={onChange}
                            className="field-space"
                            defaultValue={value?.toString()}
                            label={text}
                            type="text"
                            error={error?.message}
                            required
                        />
                    )}
                />
            )}
        </>
    );
};
