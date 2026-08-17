import { useEffect, useState } from 'react';

import { Label, Radio } from '@trussworks/react-uswds';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

import { CreateNumericQuestionRequest, ValueSetControllerService, ValueSetOption } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { Option } from 'generated';
import { maxLengthRule } from 'validation/entry';

import { AdditionalQuestionFields } from '../QuestionForm';
import styles from '../question-form.module.scss';

type Props = {
    maskOptions: Option[];
    editing?: boolean;
    published?: boolean;
};

const UNIT_TYPE_OPTIONS = [
                                    { value: 'literal', name: 'Literal value' },
                                    { value: 'coded', name: 'Coded value' },
                                ]

export const NumericFields = ({ maskOptions, editing = false, published = false }: Props) => {
    const form = useFormContext<CreateNumericQuestionRequest & AdditionalQuestionFields>();
    const [mask, relatedUnits, unitType] = useWatch({
        control: form.control,
        name: ['mask', 'relatedUnits', 'unitType'],
        exact: true,
    });
    const [numericMaskOptions, setNumericMaskOptions] = useState<Option[]>([]);
    const [relatedUnitsToggle, setRelatedUnitsToggle] = useState(unitType !== undefined && unitType !== '');
    const [valueSets, setValueSets] = useState<ValueSetOption[]>([]);

    useEffect(() => {
        ValueSetControllerService.findValueSetOptions().then((response) => setValueSets(response));
        if (!editing) {
            form.reset({
                ...form.getValues(),
                fieldLength: undefined,
                defaultValue: undefined,
                relatedUnits: false,
                unitType: undefined,
                relatedUnitsLiteral: undefined,
                relatedUnitsValueSet: undefined,
                mask: CreateNumericQuestionRequest.mask.NUM,
            });
        }
    }, []);

    useEffect(() => {
        setNumericMaskOptions(
            maskOptions.filter((m) => m.value.includes('NUM')).sort((a, b) => a.name.localeCompare(b.name))
        );
    }, [maskOptions]);

    useEffect(() => {
        if (mask !== CreateNumericQuestionRequest.mask.NUM) {
            form.resetField('fieldLength');
        }
    }, [mask]);

    useEffect(() => {
        form.resetField('relatedUnitsLiteral');
        form.resetField('relatedUnitsValueSet');
        if (!relatedUnits) {
            form.resetField('unitType');
        }
    }, [unitType, relatedUnits]);

    return (
        <>
            <Controller
                control={form.control}
                name="mask"
                rules={{ required: { value: !published, message: 'Mask is required' } }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Mask"
                        onChange={(v) => {
                            onChange(v?.value ?? null);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        value={numericMaskOptions.find(o => o.value === value)}
                        options={numericMaskOptions}
                        error={error?.message}
                        name={name}
                        id={name}
                        disabled={published}
                        required={!published}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="fieldLength"
                rules={{
                    required: {
                        value: !published && mask === CreateNumericQuestionRequest.mask.NUM,
                        message: 'Field length is required',
                    },
                    ...maxLengthRule(10),
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
                        disabled={published || mask !== CreateNumericQuestionRequest.mask.NUM}
                        required={!published && mask === CreateNumericQuestionRequest.mask.NUM}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="defaultValue"
                rules={maxLengthRule(10)}
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
                        disabled={published}
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
                        disabled={published}
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
                        setRelatedUnitsToggle(true);
                        form.setValue('relatedUnits', true);
                    }}
                    checked={relatedUnitsToggle}
                    disabled={published}
                />
                <Radio
                    id="allowFutureDates no"
                    name="allowFutureDates no"
                    value="no"
                    label="No"
                    onChange={() => {
                        setRelatedUnitsToggle(false);
                        form.setValue('relatedUnits', false);
                    }}
                    checked={!relatedUnitsToggle}
                    disabled={published}
                />
            </div>
            {relatedUnits && (
                <>
                    <Controller
                        control={form.control}
                        name="unitType"
                        rules={{ required: { value: !published, message: 'Unit type is required' } }}
                        render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                            <SingleSelect
                                label="Units type"
                                onChange={(v) => {
                                    onChange(v?.value ?? null);
                                    onBlur();
                                }}
                                onBlur={onBlur}
                                value={UNIT_TYPE_OPTIONS.find(o => o.value === value)}
                                options={UNIT_TYPE_OPTIONS}
                                name={name}
                                id={name}
                                error={error?.message}
                                required={!published}
                                disabled={published}
                            />
                        )}
                    />
                    {unitType === 'literal' && (
                        <Controller
                            control={form.control}
                            name="relatedUnitsLiteral"
                            rules={{
                                required: { value: !published, message: 'Literal units value is required' },
                                ...maxLengthRule(50),
                            }}
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
                                    required={!published}
                                    disabled={published}
                                />
                            )}
                        />
                    )}
                    {unitType === 'coded' && (
                        <Controller
                            control={form.control}
                            name="relatedUnitsValueSet"
                            rules={{
                                required: { value: !published, message: 'Related units value set is required' },
                                ...maxLengthRule(50),
                            }}
                            render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                                <SingleSelect
                                    label="Related units value set"
                                    onChange={(v) => {
                                        onChange(valueSets.find(vs => vs.value === v?.value)?.id);
                                        onBlur();
                                    }}
                                    onBlur={onBlur}
                                    value={valueSets.find(vs => vs.id === value)}
                                    options={valueSets}
                                    error={error?.message}
                                    name={name}
                                    id={name}
                                    required={!published}
                                    disabled={published}
                                />
                            )}
                        />
                    )}
                </>
            )}
        </>
    );
};
