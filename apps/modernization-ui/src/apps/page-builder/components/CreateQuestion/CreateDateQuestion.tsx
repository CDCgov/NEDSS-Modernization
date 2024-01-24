import React from 'react';
import './CreateQuestion.scss';
import { Radio } from '@trussworks/react-uswds';
import { Control, Controller } from 'react-hook-form';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    MessagingInfo,
    ReportingInfo
} from '../../generated';
import { optionsType, QuestionFormType } from './CreateQuestion';

type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

type DateQuestionProps = {
    control?: Control<CreateQuestionFormType, any>;
    options: optionsType[];
};

export const CreateDateQuestion = ({ control, options }: DateQuestionProps) => {
    const maskOption = options.filter((opt) => opt.value?.includes('DATE')) || [];

    return (
        <>
            <Controller
                control={control}
                name="mask"
                defaultValue="DATE"
                rules={{ required: { value: true, message: 'Date format required' } }}
                render={({ field: { onChange }, fieldState: { error } }) => (
                    <SelectInput
                        defaultValue="DATE"
                        className="date-format"
                        value="DATE"
                        label="Date format"
                        onChange={onChange}
                        disabled
                        options={maskOption}
                        error={error?.message}
                        required
                    />
                )}
            />
            <br></br>
            <label className="margin-top-1em">
                Allow for future dates <span className="mandatory-indicator">*</span>
            </label>
            <Controller
                control={control}
                name="allowFutureDates"
                render={({ field: { onChange, value } }) => (
                    <div className="radio-group">
                        <Radio
                            id="allowFutureDates_Y"
                            name="allowFutureDates"
                            value="Yes"
                            label="Yes"
                            onChange={(e: any) => onChange(e.target.value)}
                            checked={value === 'Yes'}
                        />
                        <Radio
                            id="allowFutureDates_N"
                            name="allowFutureDates"
                            value="No"
                            label="No"
                            onChange={(e: any) => onChange(e.target.value)}
                            checked={value === 'No'}
                        />
                    </div>
                )}
            />
        </>
    );
};
