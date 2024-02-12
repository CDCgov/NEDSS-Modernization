import { Radio } from '@trussworks/react-uswds';
import { ValueSetCreateRequest } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import styles from './valueset-form.module.scss';

type Props = {
    isEditing?: boolean;
};
export const ValuesetForm = ({ isEditing = false }: Props) => {
    const form = useFormContext<ValueSetCreateRequest>();

    return (
        <div className={styles.valuesetForm}>
            <div className={styles.formWrapper}>
                <div className={styles.heading}>Value set details</div>
                <div className={styles.subHeading}>
                    These fields will not be displayed to your users, it only makes it easier for others to search for
                    this question in the Value set library
                </div>
                <div>
                    All fields with <span className={styles.mandatory}>*</span> are required
                </div>
                <Controller
                    control={form.control}
                    name="valueSetType"
                    render={({ field: { onChange, value } }) => (
                        <div className={styles.radioButtons}>
                            <Radio
                                id="codeSet_LOCAL"
                                name="codeSet"
                                value={'LOCAL'}
                                label="LOCAL"
                                onChange={onChange}
                                checked={value === 'LOCAL'}
                                disabled={isEditing}
                            />
                            <Radio
                                id="codeSet_PHIN"
                                name="codeSet"
                                value={'PHIN'}
                                label="PHIN"
                                onChange={onChange}
                                checked={value === 'PHIN'}
                                disabled={isEditing}
                            />
                        </div>
                    )}
                />
                <Controller
                    control={form.control}
                    name="valueSetCode"
                    rules={{
                        pattern: { value: /^\w*$/, message: 'Valid characters are A-Z, a-z, 0-9, or _' },
                        required: { value: true, message: 'Value set code is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Value set code"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            disabled={isEditing}
                            required
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="valueSetName"
                    rules={{
                        pattern: { value: /^\w*$/, message: 'Valid characters are A-Z, a-z, 0-9, or _' },
                        required: { value: true, message: 'Value set name is required' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Value set name"
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
                    name="valueSetDescription"
                    rules={{
                        required: { value: true, message: 'Value set description is required' },
                        ...maxLengthRule(2000)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            label="Value set description"
                            type="text"
                            error={error?.message}
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
