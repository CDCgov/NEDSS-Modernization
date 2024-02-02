import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, UseFormReturn, useFormContext, useWatch } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { CreateTextQuestionRequest } from 'apps/page-builder/generated';

type Props = {
    maskOptions: Option[];
};
export const TextFields = ({ maskOptions }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch<CreateTextQuestionRequest>(form as UseFormReturn<CreateTextQuestionRequest>);
    const [textMaskOptions, setTextMaskOptions] = useState<Option[]>([]);

    useEffect(() => {
        form.setValue('mask', CreateTextQuestionRequest.mask.TXT);
        form.resetField('fieldLength');
        form.resetField('defaultValue');
    }, []);

    useEffect(() => {
        setTextMaskOptions(
            maskOptions
                .filter((m) => m.value.includes('TXT') || m.value === 'CENSUS_TRACT')
                .sort((a, b) => a.name.localeCompare(b.name))
        );
    }, [maskOptions]);

    useEffect(() => {
        if (watch.mask !== 'TXT') {
            form.resetField('fieldLength');
        }
    }, [watch.mask]);

    return (
        <>
            <Controller
                control={form.control}
                name="mask"
                rules={{ required: { value: true, message: 'Mask is required' } }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Mask"
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={textMaskOptions}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="fieldLength"
                rules={{
                    required: { value: true, message: 'Field length is required' },
                    ...maxLengthRule(4)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Field length"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value?.toString()}
                        type="number"
                        error={error?.message}
                        min={1}
                        max={2000}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={watch.mask !== 'TXT'}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="defaultValue"
                rules={maxLengthRule(50)}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Default value"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value?.toString()}
                        type="text"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                    />
                )}
            />
        </>
    );
};
