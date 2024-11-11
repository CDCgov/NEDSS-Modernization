import { Icon, Tooltip } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Checkbox } from 'design-system/checkbox';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../DataElement';
import styles from './data-elements-form.module.scss';
import { useEffect } from 'react';

type Props = {
    fieldName: string;
    field: keyof DataElements;
};
export const DataElementRow = ({ fieldName, field }: Props) => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    const calculateOddsRatio = () => {
        const m = watch[field]?.m;
        const u = watch[field]?.u;
        if (Number.isNaN(m) || Number.isNaN(u) || u == 0 || m == 0 || m == undefined || u == undefined) {
            return '--';
        }
        return m / u;
    };

    useEffect(() => {
        const active = watch[field]?.active;
        if (active !== undefined) {
            if (!active) {
                form.setValue(field, { active, m: undefined, u: undefined, threshold: undefined });
                form.clearErrors(field);
            } else {
                form.resetField(field);
            }
        }
    }, [watch[field]?.active]);

    useEffect(() => {
        const m = Number(watch[field]?.m);
        const u = Number(watch[field]?.u);
        if (Number.isNaN(m) || Number.isNaN(u) || u == 0 || m == 0 || m == undefined || u == undefined) {
            form.setValue(`${field}.logOdds`, undefined);
        } else {
            form.setValue(`${field}.logOdds`, Math.log(m) - Math.log(u));
        }
    }, [watch[field]?.m, watch[field]?.u]);

    return (
        <tr>
            <td className={styles.checkbox}>
                <Controller
                    control={form.control}
                    name={`${field}.active`}
                    render={({ field: { value, onChange, name } }) => (
                        <Checkbox name={name} label="" id={`${field}-checkbox`} selected={value} onChange={onChange} />
                    )}
                />
            </td>
            <td className={styles.field}>{fieldName}</td>
            <td>
                <Controller
                    control={form.control}
                    name={`${field}.m`}
                    rules={{
                        required: { value: watch[field]?.active ?? false, message: 'M is required' }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <div className={styles.inputContainer}>
                            <input
                                type="number"
                                onChange={onChange}
                                onBlur={onBlur}
                                value={value ?? ''}
                                name={name}
                                max={1}
                                min={0}
                                step={0.01}
                                disabled={!watch[field]?.active}
                                className={classNames([error?.message ? styles.errorBorder : '', styles.numericInput])}
                            />
                            {error?.message && (
                                <Tooltip
                                    id={`${field}-m-error`}
                                    label={error.message}
                                    className={styles.tooltip}
                                    position={'top'}>
                                    <Icon.ErrorOutline className={styles.tooltipIcon} />
                                </Tooltip>
                            )}
                        </div>
                    )}
                />
            </td>
            <td>
                <Controller
                    control={form.control}
                    name={`${field}.u`}
                    rules={{
                        required: { value: watch[field]?.active ?? false, message: 'U is required' }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <div className={styles.inputContainer}>
                            <input
                                type="number"
                                onChange={onChange}
                                onBlur={onBlur}
                                value={value ?? ''}
                                name={name}
                                max={1}
                                min={0}
                                step={0.01}
                                disabled={!watch[field]?.active}
                                className={classNames([error?.message ? styles.errorBorder : '', styles.numericInput])}
                            />
                            {error?.message && (
                                <Tooltip
                                    id={`${field}-u-error`}
                                    label={error.message}
                                    className={styles.tooltip}
                                    position={'top'}>
                                    <Icon.ErrorOutline className={styles.tooltipIcon} />
                                </Tooltip>
                            )}
                        </div>
                    )}
                />
            </td>
            <td>{calculateOddsRatio()}</td>
            <td>{watch[field]?.logOdds ?? '--'}</td>
            <td>
                <Controller
                    control={form.control}
                    name={`${field}.threshold`}
                    rules={{
                        required: {
                            value: watch[field]?.active ?? false,
                            message: 'Missing threshold'
                        }
                    }}
                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                        <div className={styles.inputContainer}>
                            <input
                                type="number"
                                onChange={onChange}
                                onBlur={onBlur}
                                value={value ?? ''}
                                name={name}
                                min={0}
                                step={0.01}
                                disabled={!watch[field]?.active}
                                className={classNames([error?.message ? styles.errorBorder : '', styles.numericInput])}
                            />
                            {error?.message && (
                                <Tooltip
                                    id={`${field}-threshold-error`}
                                    label={error.message}
                                    className={styles.tooltip}
                                    position={'top'}>
                                    <Icon.ErrorOutline className={styles.tooltipIcon} />
                                </Tooltip>
                            )}
                        </div>
                    )}
                />
            </td>
            <td></td>
        </tr>
    );
};
