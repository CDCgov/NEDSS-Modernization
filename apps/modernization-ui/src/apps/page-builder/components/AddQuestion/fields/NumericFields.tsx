import { Label, Radio } from '@trussworks/react-uswds';
import { CreateNumericQuestionRequest, ValueSetControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Input } from 'components/FormInputs/Input';
import { SelectInput, Selectable } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import styles from '../question-form.module.scss';
import { AdditionalQuestionFields } from '../QuestionForm';

type Props = {
    maskOptions: Option[];
    editing?: boolean;
    published?: boolean;
};
export const NumericFields = ({ maskOptions, editing = false, published = false }: Props) => {
    const form = useFormContext<CreateNumericQuestionRequest & AdditionalQuestionFields>();
    const [mask, relatedUnits, unitType] = useWatch({
        control: form.control,
        name: ['mask', 'relatedUnits', 'unitType'],
        exact: true
    });
    const [numericMaskOptions, setNumericMaskOptions] = useState<Option[]>([]);
    const [relatedUnitsToggle, setRelatedUnitsToggle] = useState(unitType !== undefined || unitType !== '');
    const [valueSets, setValueSets] = useState<Selectable[]>([]);

    useEffect(() => {
        ValueSetControllerService.findValueSetOptionsUsingGet({ authorization: authorization() }).then((response) =>
            setValueSets(response)
        );
        if (!editing) {
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
                        message: 'Field length is required'
                    },
                    ...maxLengthRule(10)
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
                            <SelectInput
                                label="Units type"
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
                                ...maxLengthRule(50)
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
                                ...maxLengthRule(50)
                            }}
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
