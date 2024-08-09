import { Controller, useWatch, useFormContext } from 'react-hook-form';
import { Button, Icon } from '@trussworks/react-uswds';
import { CodedValue } from 'coded';
import FormCard from 'components/FormCard/FormCard';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SingleSelect } from 'design-system/select';
import styles from './name-fields-extended.module.scss';
import { useEffect, useState } from 'react';
import { NameFieldsTable } from './NameFieldsTable';
import classNames from 'classnames';
import { EntryWrapper } from 'components/Entry';

type CodedValues = {
    suffixes: CodedValue[];
};

type Props = { coded: CodedValues };

export const NameFieldsExtended = ({ coded }: Props) => {
    const { control } = useFormContext();
    const [isValid, setIsValid] = useState(false);
    const nameType = useWatch({ control: control, name: 'type' });
    const nameTime = useWatch({ control: control, name: 'nameAsOf' });

    useEffect(() => {
        if (nameType && nameTime) {
            setIsValid(true);
        } else {
            setIsValid(false);
        }
    }, [nameType, nameTime]);

    return (
        <div className={styles.name}>
            <FormCard id="section-Name" title="Name" required="All required fields for adding name">
                <div className={styles.names}>
                    <NameFieldsTable />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="nameAsOf"
                        rules={{
                            required: { value: true, message: 'Effective from time is required' }
                        }}
                        render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                label="Name as of"
                                name={name}
                                onChange={onChange}
                                onBlur={onBlur}
                                required
                                errorMessage={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="type"
                        rules={{
                            required: { value: true, message: 'Effective from time is required' }
                        }}
                        render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                            <SingleSelect
                                id={name}
                                value={value}
                                label="Type"
                                options={[
                                    { label: 'Legal', name: 'Legal', value: 'legal' },
                                    { label: 'Nickname', name: 'Nickname', value: 'nickname' }
                                ]}
                                name={name}
                                onChange={onChange}
                                onBlur={onBlur}
                                required
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="prefix"
                        render={({ field: { value, onChange, onBlur, name } }) => (
                            <SingleSelect
                                id={name}
                                value={value}
                                label="Prefix"
                                options={[
                                    { label: 'Mr.', name: 'Mr.', value: 'mr' },
                                    { label: 'Mrs.', name: 'Mrs.', value: 'mrs' },
                                    { label: 'Ms.', name: 'Ms.', value: 'ms' }
                                ]}
                                name={name}
                                onChange={onChange}
                                onBlur={onBlur}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="lastName"
                        rules={validNameRule}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                label="Last"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                name={name}
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                                className={styles.input}
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="secondLast"
                        rules={validNameRule}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                label="Second Last"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                name={name}
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="firstName"
                        rules={validNameRule}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                label="First"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="middleName"
                        rules={validNameRule}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                label="Middle"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="secondMiddle"
                        rules={validNameRule}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                label="Second middle"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                error={error?.message}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={styles.row}>
                    <Controller
                        control={control}
                        name="suffix"
                        render={({ field: { onChange, value, name } }) => (
                            <SingleSelect
                                onChange={onChange}
                                value={value}
                                name={name}
                                id={name}
                                label="Suffix"
                                options={coded.suffixes.map((suffix) => {
                                    return { value: suffix.value, name: suffix.name, label: suffix.name };
                                })}
                                sizing="compact"
                                orientation="horizontal"
                            />
                        )}
                    />
                </div>
                <div className={classNames(styles.row, styles.footer)}>
                    <EntryWrapper orientation="horizontal" htmlFor="footer" label="">
                        <Button onClick={() => console.log('ADD')} type="button" outline disabled={!isValid}>
                            <Icon.Add size={3} /> Add name
                        </Button>
                    </EntryWrapper>
                </div>
            </FormCard>
        </div>
    );
};
