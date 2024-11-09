import { Card } from 'apps/patient/add/extended/card/Card';
import classNames from 'classnames';
import { Checkbox } from 'design-system/checkbox';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../DataElement';
import { Icon, Tooltip } from '@trussworks/react-uswds';
import styles from './data-elements-form.module.scss';
import { useEffect } from 'react';

export const DataElementsForm = () => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    useEffect(() => {
        form.trigger();
    }, [watch.lastName?.active]);

    const handleToggleAll = (toggle: boolean) => {
        console.log('toggle all', toggle);
    };

    return (
        <Card id="dataElementsCard" title="Data elements">
            <div className={styles.dataElementsForm}>
                <table>
                    <thead>
                        <tr>
                            <th className={styles.checkbox}>
                                <Checkbox
                                    name={'selectAll'}
                                    label=""
                                    id={'toggle-all-checkbox'}
                                    onChange={handleToggleAll}
                                />
                            </th>
                            <th>Field</th>
                            <th>M</th>
                            <th>U</th>
                            <th>Odds ratio</th>
                            <th>Log odds</th>
                            <th>Threshold</th>
                            <th />
                        </tr>
                        <tr className={styles.border}>
                            <th colSpan={8} />
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className={styles.checkbox}>
                                <Controller
                                    control={form.control}
                                    name={'lastName.active'}
                                    render={({ field: { value, onChange, name } }) => (
                                        <Checkbox
                                            name={name}
                                            label=""
                                            id={'last-name-checkbox'}
                                            selected={value ?? false}
                                            onChange={(e) => onChange(e)}
                                        />
                                    )}
                                />
                            </td>
                            <td className={styles.field}>Last name</td>
                            <td>
                                <Controller
                                    control={form.control}
                                    name={'lastName.m'}
                                    rules={{
                                        required: { value: watch.lastName?.active ?? false, message: 'M is required' }
                                    }}
                                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                                        <div className={styles.inputContainer}>
                                            <input
                                                type="number"
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                defaultValue={value}
                                                name={name}
                                                max={1}
                                                min={0}
                                                step={0.01}
                                                disabled={!watch.lastName?.active}
                                                className={classNames([
                                                    error?.message ? styles.errorBorder : '',
                                                    styles.numericInput
                                                ])}
                                            />
                                            {error?.message && (
                                                <Tooltip
                                                    id={'lastName.m-error'}
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
                                    name={'lastName.u'}
                                    rules={{
                                        required: { value: watch.lastName?.active ?? false, message: 'U is required' }
                                    }}
                                    render={({ field: { value, onChange, onBlur, name }, fieldState: { error } }) => (
                                        <div className={styles.inputContainer}>
                                            <input
                                                type="number"
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                defaultValue={value}
                                                name={name}
                                                max={1}
                                                min={0}
                                                step={0.01}
                                                disabled={!watch.lastName?.active}
                                                className={classNames([
                                                    error?.message ? styles.errorBorder : '',
                                                    styles.numericInput
                                                ])}
                                            />
                                            {error?.message && (
                                                <Tooltip
                                                    id={'lastName.u-error'}
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
                            <td>Odds ratio</td>
                            <td>Log odds</td>
                            <td>Threshold</td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </Card>
    );
};
