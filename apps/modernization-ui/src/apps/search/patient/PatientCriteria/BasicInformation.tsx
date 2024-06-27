import { Control, Controller } from 'react-hook-form';
import { Selectable, asValue } from 'options';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { FormGroup, Label, Checkbox, ErrorMessage } from '@trussworks/react-uswds';
import { RecordStatus } from 'generated/graphql/schema';
import styles from './basic-information.module.scss';
import { PatientCriteriaEntry } from '../criteria';

type Props = {
    control: Control<PatientCriteriaEntry>;
    handleRecordStatusChange: (
        value: Selectable[],
        status: Selectable,
        isChecked: boolean,
        onChange: (status: Selectable[]) => void
    ) => void;
};

export const BasicInformation = ({ control, handleRecordStatusChange }: Props) => {
    return (
        <div className={styles.basic}>
            <Controller
                control={control}
                name="lastName"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        onBlur={onBlur}
                        onChange={onChange}
                        type="text"
                        label="Last name"
                        name={name}
                        defaultValue={value}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="firstName"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="First name"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="dateOfBirth"
                render={({ field: { onChange, value, name } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        label="Date of birth"
                    />
                )}
            />
            <Controller
                control={control}
                name="gender"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        defaultValue={asValue(value)}
                        onChange={onChange}
                        name={name}
                        htmlFor={name}
                        label="Sex"
                        id={name}
                        options={[
                            { name: 'Male', value: 'M' },
                            { name: 'Female', value: 'F' },
                            { name: 'Other', value: 'U' }
                        ]}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Patient ID"
                        name={name}
                        htmlFor={name}
                        id={name}
                    />
                )}
            />
            <FormGroup error={!!control._formState.errors.status} className={styles.checkboxes}>
                <Label htmlFor={''}>Include records that are</Label>
                {control._formState.errors.status ? (
                    <ErrorMessage id="record-status-error-message">At least one status is required</ErrorMessage>
                ) : null}
                <div className={styles.boxes}>
                    <Controller
                        control={control}
                        name="status"
                        rules={{ validate: (v) => v.length > 0 }}
                        render={({ field: { onChange, value } }) => {
                            return (
                                <>
                                    <Checkbox
                                        id={'record-status-active'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(
                                                value,
                                                {
                                                    name: 'Record status',
                                                    label: 'Record status',
                                                    value: RecordStatus.Active
                                                },
                                                v.target.checked,
                                                onChange
                                            )
                                        }
                                        name={'name'}
                                        label={'Active'}
                                        checked={
                                            value.filter(function (status: Selectable) {
                                                return status.value === RecordStatus.Active;
                                            }).length !== 0
                                        }
                                    />
                                    <Checkbox
                                        id={'record-status-deleted'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(
                                                value,
                                                {
                                                    name: 'Deleted',
                                                    label: 'Deleted',
                                                    value: RecordStatus.LogDel
                                                },
                                                v.target.checked,
                                                onChange
                                            )
                                        }
                                        name={'name'}
                                        label={'Deleted'}
                                        checked={
                                            value.filter(function (status: Selectable) {
                                                return status.value === RecordStatus.LogDel;
                                            }).length !== 0
                                        }
                                    />
                                    <Checkbox
                                        id={'record-status-superceded'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(
                                                value,
                                                {
                                                    name: 'Superceded',
                                                    label: 'Superceded',
                                                    value: RecordStatus.Superceded
                                                },
                                                v.target.checked,
                                                onChange
                                            )
                                        }
                                        name={'name'}
                                        label={'Superseded'}
                                        checked={
                                            value.filter(function (status: Selectable) {
                                                return status.value === RecordStatus.Superceded;
                                            }).length !== 0
                                        }
                                    />
                                </>
                            );
                        }}
                    />
                </div>
            </FormGroup>
        </div>
    );
};
