import { DataElementCheckBox } from '../DataElementCheckBox/DataElementCheckBox';
import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../../DataElement';
import { TableNumericInput } from '../TableNumericInput/TableNumericInput';
import styles from './DataElementRow.module.scss';
import { useDataElements } from 'apps/deduplication/api/useDataElements';

type Props = {
    fieldName: string;
    field: keyof DataElements;
};

export const DataElementRow = ({ fieldName, field }: Props) => {
    const { configuration } = useDataElements();
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    useEffect(() => {
        const active = watch[field]?.active;
        if (active !== undefined) {
            if (!active) {
                form.setValue(field, {
                    active,
                    oddsRatio: undefined,
                    logOdds: undefined,
                    threshold: undefined
                });
                form.clearErrors(field);
            } else {
                const defaultValue = {
                    active,
                    oddsRatio: configuration?.[field]?.oddsRatio,
                    logOdds: 0, // Will be recalculated when oddsRatio changes
                    threshold: configuration?.[field]?.threshold
                };
                form.setValue(field, defaultValue);
            }
        }
    }, [watch[field]?.active]);

    useEffect(() => {
        const oddsRatio = Number(watch[field]?.oddsRatio);

        const isValidOddsRatio = !isNaN(oddsRatio) && oddsRatio > 0 && oddsRatio < 1;

        if (!isValidOddsRatio) {
            form.setValue(`${field}.logOdds`, undefined);
            return;
        }

        form.setValue(`${field}.logOdds`, Math.log(oddsRatio));
    }, [watch[field]?.oddsRatio]);

    return (
        <tr>
            <td className={styles.checkbox}>
                <Controller
                    control={form.control}
                    name={`${field}.active`}
                    render={({ field: { value, onChange } }) => (
                        <DataElementCheckBox
                            name={`${field}.active`}
                            label=""
                            id={`${field}-checkbox`}
                            selected={value}
                            onChange={onChange}
                            data-testid={`${field}-checkbox`}
                        />
                    )}
                />
            </td>
            <td className={styles.field}>{fieldName}</td>
            <td>
                <Controller
                    control={form.control}
                    name={`${field}.oddsRatio`}
                    rules={{
                        required: {
                            value: watch[field]?.active ?? false,
                            message: 'Missing odds ratio'
                        },
                        validate: (value) => {
                            if (String(value) === '') return true; // Allow empty input to clear error
                            const numericValue = Number(value);
                            if (isNaN(numericValue) || numericValue <= 0 || numericValue >= 1) {
                                return 'Requires a value between 0 and 1';
                            }
                            return true;
                        }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <TableNumericInput
                            name={name}
                            value={value ?? ''}
                            onChange={(e) => {
                                onChange(e); // Normal onChange event
                                form.trigger(`${field}.oddsRatio`); // Manually trigger validation
                            }}
                            onBlur={onBlur} // Validation triggered only onBlur
                            error={error?.message}
                            max={10}
                            min={0.01} // Prevents division by zero
                            step={0.01}
                            disabled={!watch[field]?.active}
                            data-testid={`${field}-oddsRatio`}
                        />
                    )}
                />
            </td>
            <td>{watch[field]?.logOdds ?? '--'}</td>
            <td>
                <Controller
                    control={form.control}
                    name={`${field}.threshold`}
                    rules={{
                        required: {
                            value: watch[field]?.active ?? false,
                            message: 'Missing threshold'
                        },
                        validate: (value) => {
                            if (String(value) === '') return true; // Allow empty input to clear error
                            const numericValue = Number(value);
                            if (isNaN(numericValue) || numericValue <= 0 || numericValue >= 1) {
                                return 'Requires a value between 0 and 1';
                            }
                            return true;
                        }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <TableNumericInput
                            name={name}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur} // Validation triggered only onBlur
                            error={error?.message}
                            max={1}
                            min={0}
                            step={0.01}
                            disabled={!watch[field]?.active}
                            data-testid={`${field}-threshold`}
                        />
                    )}
                />
            </td>
            <td></td>
        </tr>
    );
};
