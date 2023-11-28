import React from 'react';
import './CreateQuestion.scss';
import { Control, Controller } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';
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
type TextQuestionProps = {
    control?: Control<CreateQuestionFormType, any>;
    isText: boolean;
};
const textMask = [
    {
        value: 'TXT',
        name: 'Alphanumeric (no defined mask) Field length'
    },
    {
        value: 'CENSUS_TRACT',
        name: 'Census Tract (^(d{4}|d{4}.(?!99)d{2})$)'
    },
    {
        value: 'TXT_EMAIL',
        name: 'Email Address (^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$)'
    },
    {
        value: 'TXT_ID10',
        name: 'ID Number (10-alphanumeric)'
    },
    {
        value: 'TXT_ID12',
        name: 'ID Number (12-alphanumeric)'
    },
    {
        value: 'TXT_ID15',
        name: 'ID Number (15-alphanumeric)'
    },
    {
        value: 'TXT_PHONE',
        name: 'Phone Number (999-000-0000)'
    },
    {
        value: 'TXT_SSN',
        name: 'Social Security Number (SSN)'
    },
    {
        value: 'TXT_IDTB',
        name: 'TB CDC Case Number (YYYY->LL-AAAAAAAAA)'
    },
    {
        value: 'TXT_ZIP',
        name: 'Zip Code (00000-9999)'
    }
];

const numMask = [
    {
        name: '2-digit Day (DD)',
        value: 'NUM_DD'
    },
    {
        name: '2-digit Month (MM)',
        value: 'NUM_MM'
    },
    {
        name: '4-digit Year (YYYY)',
        value: 'NUM_YYYY'
    },
    {
        name: 'Integer (no defined mask)',
        value: 'NUM'
    },
    {
        name: 'Phone Number Extenstion (99999999)',
        value: 'EXT'
    },
    {
        name: 'Structured  Numeric (e.g, >0.5)',
        value: 'NUM_SN'
    },
    {
        name: 'NUM_TEMP Temperature (999.9)',
        value: 'NUM_TEMP'
    }
];
export const CreateTextQuestion = ({ control, isText }: TextQuestionProps) => {
    const maskOption = isText ? textMask : numMask;
    return (
        <>
            <Controller
                control={control}
                name="mask"
                rules={{ required: { value: true, message: 'Mask required' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        defaultValue={value}
                        label="Mask"
                        onChange={onChange}
                        options={maskOption}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="fieldLength"
                rules={{
                    required: { value: true, message: 'Field length required' },
                    maxLength: 3
                }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        className="field-space"
                        defaultValue={value?.toString()}
                        label="Field length"
                        type="number"
                        error={error?.message}
                        required
                    />
                )}
            />
        </>
    );
};
