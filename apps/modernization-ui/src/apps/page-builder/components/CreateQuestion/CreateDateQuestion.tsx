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
import { QuestionFormType } from './CreateQuestion';

type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

type DateQuestionProps = {
    control?: Control<CreateQuestionFormType, any>;
};

export const CreateDateQuestion = ({ control }: DateQuestionProps) => {
    return (
        <>
            <Controller
                control={control}
                name="dateFormat"
                rules={{ required: { value: true, message: 'Mask required' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        defaultValue={value}
                        label="Date format"
                        onChange={onChange}
                        options={[
                            {
                                name: 'Generic date (MM/DD/YYYY)',
                                value: 'MM/DD/yyyy'
                            }
                        ]}
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
