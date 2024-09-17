import { Heading } from 'components/heading';
import styles from './Administrative.module.scss';
import { Controller, useFormContext } from 'react-hook-form';
import classNames from 'classnames';
import { AdministrativeEntry } from 'apps/patient/data/entry';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';

export const Administrative = () => {
    const { control } = useFormContext<{ administrative: AdministrativeEntry }>();

    return (
        <section id="administrative" className={styles.input}>
            <header>
                <Heading level={2}>Administrative</Heading>
            </header>
            <div className={classNames(styles.form)}>
                <Controller
                    control={control}
                    name="administrative.asOf"
                    rules={{ required: { value: true, message: 'As of date is required.' } }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <DatePickerInput
                            label="Information as of date"
                            orientation="horizontal"
                            defaultValue={value}
                            onBlur={onBlur}
                            onChange={onChange}
                            name={name}
                            disableFutureDates
                            errorMessage={error?.message}
                            required
                        />
                    )}
                />

                <Controller
                    control={control}
                    name="administrative.comment"
                    rules={{
                        ...maxLengthRule(2000)
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <>
                            <Input
                                label="General comments"
                                type="text"
                                orientation="horizontal"
                                onBlur={onBlur}
                                onChange={onChange}
                                defaultValue={value}
                                name={name}
                                htmlFor={name}
                                id={name}
                                error={error?.message}
                                multiline
                            />
                        </>
                    )}
                />
            </div>
        </section>
    );
};
