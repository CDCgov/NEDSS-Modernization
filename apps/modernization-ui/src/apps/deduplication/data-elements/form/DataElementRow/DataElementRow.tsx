import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../../../api/model/DataElement';
import { TableNumericInput } from '../TableNumericInput/TableNumericInput';
import styles from './DataElementRow.module.scss';
import { Checkbox } from 'design-system/checkbox';

type Props = {
    fieldName: string;
    dataElements?: DataElements;
    field: keyof DataElements;
};

export const DataElementRow = ({ fieldName, field, dataElements }: Props) => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    useEffect(() => {
        const active = watch[field]?.active;
        if (active !== undefined) {
            if (!active) {
                form.setValue(field, {
                    active,
                    oddsRatio: undefined,
                    logOdds: undefined
                });
                form.clearErrors(field);
            } else {
                const defaultValue = {
                    active,
                    oddsRatio: dataElements?.[field]?.oddsRatio,
                    logOdds: 0 // Will be recalculated when oddsRatio changes
                };
                form.setValue(field, defaultValue);
            }
            // triggers the onBlur of the form, updating the save disabled state
            form.trigger(`${field}.active`);
        }
    }, [watch[field]?.active]);

    useEffect(() => {
        const oddsRatio = Number(watch[field]?.oddsRatio);
        if (oddsRatio == undefined || isNaN(oddsRatio) || oddsRatio == 0) {
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
                    render={({ field: { value, onChange, onBlur } }) => (
                        <Checkbox
                            name={`${field}.active`}
                            label=""
                            id={`${field}-checkbox`}
                            selected={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            aria-label={fieldName}
                            aria-checked={value}
                            aria-labelledby={`${field}-checkbox-label`}
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
                        }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <TableNumericInput
                            name={name}
                            value={value ?? ''}
                            onChange={onChange} // No longer triggering validation
                            onBlur={onBlur}
                            error={error?.message}
                            min={0.01} // Prevents division by zero
                            step={0.01}
                            disabled={!watch[field]?.active}
                        />
                    )}
                />
            </td>
            <td>{watch[field]?.logOdds ?? '--'}</td>
        </tr>
    );
};
