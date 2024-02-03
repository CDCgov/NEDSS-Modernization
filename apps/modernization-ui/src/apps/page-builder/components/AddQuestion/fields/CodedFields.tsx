import { CreateCodedQuestionRequest, ValueSetControllerService, ValueSetOption } from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { authorization } from 'authorization';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

export const CodedFields = () => {
    const form = useFormContext<CreateCodedQuestionRequest>();
    const watch = useWatch(form);
    const [valueSets, setValueSets] = useState<ValueSetOption[]>([]);
    const [options, setOptions] = useState<Option[]>([]);

    useEffect(() => {
        ValueSetControllerService.findValueSetOptionsUsingGet({ authorization: authorization() }).then((response) => {
            setValueSets(response);
        });
        form.setValue('valueSet', -1);
        form.resetField('defaultValue');
    }, []);

    useEffect(() => {
        const selected = valueSets.find((v) => v.value === watch.valueSet?.toString());
        selected && useOptions(selected.codeSetNm).then((response) => setOptions(response.options));
    }, [watch.valueSet]);

    return (
        <>
            <Controller
                control={form.control}
                name="valueSet"
                rules={{
                    required: { value: true, message: 'Value set is required' }
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Value set"
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={valueSets}
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
                name="defaultValue"
                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Default value"
                        onChange={onChange}
                        defaultValue={value}
                        options={options}
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
