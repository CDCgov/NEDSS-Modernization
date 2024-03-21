import { Label, Radio, Textarea } from '@trussworks/react-uswds';
import { CreateConceptRequest } from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ChangeEvent, useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { isAfter } from 'validation/date/isAfter';
import { maxLengthRule } from 'validation/entry';
import styles from './concept-form.module.scss';

type Props = {
    isEditing?: boolean;
};
export const ConceptForm = ({ isEditing = false }: Props) => {
    const form = useFormContext<CreateConceptRequest>();
    const { options: codeSystems } = useOptions('CODE_SYSTEM');
    const effectiveFrom = useWatch({ control: form.control, name: 'effectiveFromTime' });

    useEffect(() => {
        form.trigger('effectiveToTime');
    }, [effectiveFrom]);

    return (
        <div className={styles.conceptForm}>
            <div className={styles.formContent}>
                <Controller
                    control={form.control}
                    name="localCode"
                    rules={{
                        required: { value: !isEditing, message: 'Local code is required' },
                        pattern: { value: /^\w*$/, message: 'Valid characters are A-Z, a-z, 0-9, or _' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={(e: ChangeEvent<HTMLInputElement>) => {
                                onChange(e);
                                form.setValue('conceptCode', e.target.value);
                            }}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Local code"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required={!isEditing}
                            disabled={isEditing}
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="longName"
                    rules={{
                        required: { value: true, message: 'Long display name is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={(e: ChangeEvent<HTMLInputElement>) => {
                                onChange(e);
                                form.setValue('conceptName', e.target.value);
                            }}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Long display name"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="display"
                    rules={{
                        required: { value: true, message: 'UI display name is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={(e: ChangeEvent<HTMLInputElement>) => {
                                onChange(e);
                                form.setValue('preferredConceptName', e.target.value);
                            }}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="UI display name"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
                <div className={styles.effectiveTimeWrapper}>
                    <Controller
                        control={form.control}
                        name="effectiveFromTime"
                        rules={{
                            required: { value: true, message: 'Effective from time is required' }
                        }}
                        render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                label="Effective from time"
                                onChange={onChange}
                                onBlur={onBlur}
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={form.control}
                        name="effectiveToTime"
                        rules={{ validate: isAfter(effectiveFrom) }}
                        render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                label="Effective to time"
                                onChange={onChange}
                                onBlur={onBlur}
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </div>
                <Controller
                    control={form.control}
                    name="status"
                    render={({ field: { value } }) => (
                        <div className={styles.radioButtons}>
                            <Radio
                                id="status_ACTIVE"
                                name="status"
                                value={CreateConceptRequest.status.ACTIVE}
                                label="ACTIVE"
                                onChange={() => {
                                    form.setValue('status', CreateConceptRequest.status.ACTIVE, {
                                        shouldDirty: true,
                                        shouldValidate: true
                                    });
                                }}
                                checked={value === CreateConceptRequest.status.ACTIVE}
                            />
                            <Radio
                                id="status_INACTIVE"
                                name="status"
                                value={CreateConceptRequest.status.INACTIVE}
                                label="INACTIVE"
                                onChange={() => {
                                    form.setValue('status', CreateConceptRequest.status.INACTIVE, {
                                        shouldDirty: true,
                                        shouldValidate: true
                                    });
                                }}
                                checked={value === CreateConceptRequest.status.INACTIVE}
                            />
                        </div>
                    )}
                />
                <Controller
                    control={form.control}
                    name="adminComments"
                    rules={{
                        ...maxLengthRule(2000)
                    }}
                    render={({ field: { onChange, value, onBlur, name } }) => (
                        <>
                            <Label htmlFor={name}>Administrative comments</Label>
                            <Textarea
                                className={styles.wideInput}
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                name={name}
                                id={name}
                            />
                        </>
                    )}
                />

                <div className={styles.sectionHeading}>Messaging concept code</div>
                <Controller
                    control={form.control}
                    name="conceptCode"
                    rules={{
                        required: { value: true, message: 'Concept code is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Concept code"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="conceptName"
                    rules={{
                        required: { value: true, message: 'Concept name is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Concept name"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="preferredConceptName"
                    rules={{
                        required: { value: true, message: 'Preferred concept name is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            className={styles.wideInput}
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Preferred concept name"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="codeSystem"
                    rules={{ required: { value: true, message: 'Code system name is required' } }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <SelectInput
                            className={styles.wideInput}
                            label="Code system name"
                            defaultValue={value}
                            onChange={(e) => {
                                onChange(e);
                                onBlur();
                            }}
                            onBlur={onBlur}
                            error={error?.message}
                            options={codeSystems}
                            name={name}
                            htmlFor={name}
                            id={name}
                            required
                        />
                    )}
                />
            </div>
        </div>
    );
};
