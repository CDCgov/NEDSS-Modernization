import { Label, Radio } from '@trussworks/react-uswds';
import { CreateNumericQuestionRequest, ValueSetControllerService } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput, Selectable } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, UseFormReturn, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';
import { authorization } from 'authorization';

type Props = {
    maskOptions: Option[];
};
export const NumericFields = ({ maskOptions }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch<CreateQuestionForm & CreateNumericQuestionRequest>(
        form as UseFormReturn<CreateNumericQuestionRequest>
    );
    const [numericMaskOptions, setNumericMaskOptions] = useState<Option[]>([]);
    const [relatedUnits, setRelatedUnits] = useState(false);
    const [valueSets, setValueSets] = useState<Selectable[]>([]);

    useEffect(() => {
        ValueSetControllerService.findValueSetOptionsUsingGet({ authorization: authorization() }).then((response) =>
            setValueSets(response)
        );

        form.reset({
            ...form.getValues(),
            fieldLength: undefined,
            defaultValue: undefined,
            relatedUnits: false,
            unitType: undefined,
            relatedUnitsLiteral: undefined,
            relatedUnitsValueSet: undefined,
            mask: CreateNumericQuestionRequest.mask.NUM
        });
    }, []);

    useEffect(() => {
        setNumericMaskOptions(
            maskOptions.filter((m) => m.value.includes('NUM')).sort((a, b) => a.name.localeCompare(b.name))
        );
    }, [maskOptions]);

    useEffect(() => {
        if (watch.mask !== CreateNumericQuestionRequest.mask.NUM) {
            form.resetField('fieldLength');
        }
    }, [watch.mask]);

    useEffect(() => {
        form.resetField('relatedUnitsLiteral');
        form.resetField('relatedUnitsValueSet');
        if (!watch.relatedUnits) {
            form.resetField('unitType');
        }
    }, [watch.unitType, watch.relatedUnits]);

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
                        options={numericMaskOptions}
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
                        max={300}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={watch.mask !== CreateNumericQuestionRequest.mask.NUM}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="defaultValue"
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Default value"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value?.toString()}
                        type="number"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="minValue"
                rules={maxLengthRule(50)}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Minimum value"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value?.toString()}
                        type="number"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="maxValue"
                rules={maxLengthRule(50)}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Maximum value"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value?.toString()}
                        type="number"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                    />
                )}
            />
            <Label htmlFor="relatedUnits" className="required">
                Related units
            </Label>
            <div className={styles.yesNoRadioButtons}>
                <Radio
                    id="relatedUnits yes"
                    name="relatedUnits yes"
                    value="yes"
                    label="Yes"
                    onChange={() => {
                        setRelatedUnits(true);
                        form.setValue('relatedUnits', true);
                    }}
                    checked={relatedUnits}
                />
                <Radio
                    id="allowFutureDates no"
                    name="allowFutureDates no"
                    value="no"
                    label="No"
                    onChange={() => {
                        setRelatedUnits(false);
                        form.setValue('relatedUnits', false);
                    }}
                    checked={!relatedUnits}
                />
            </div>
            {watch.relatedUnits && (
                <>
                    <Controller
                        control={form.control}
                        name="unitType"
                        rules={{ required: { value: true, message: 'Unit type is required' } }}
                        render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Unit type"
                                onChange={(e) => {
                                    onChange(e);
                                    onBlur();
                                }}
                                onBlur={onBlur}
                                defaultValue={value}
                                options={[
                                    { value: 'literal', name: 'Literal value' },
                                    { value: 'coded', name: 'Coded value' }
                                ]}
                                name={name}
                                id={name}
                                htmlFor={name}
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    {watch.unitType === 'literal' && (
                        <Controller
                            control={form.control}
                            name="relatedUnitsLiteral"
                            render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                                <Input
                                    label="Literal units value"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    defaultValue={value?.toString()}
                                    type="text"
                                    error={error?.message}
                                    name={name}
                                    id={name}
                                    htmlFor={name}
                                    required
                                />
                            )}
                        />
                    )}
                    {watch.unitType === 'coded' && (
                        <>
                            <Controller
                                control={form.control}
                                name="relatedUnitsValueSet"
                                rules={{ required: { value: true, message: 'Related units value set is required' } }}
                                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                                    <SelectInput
                                        label="Related units value set"
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
                        </>
                    )}
                </>
            )}
        </>
    );
};
