import { useEffect, useState } from 'react';

import { Controller, useFieldArray, useFormContext, useWatch } from 'react-hook-form';

import { Batch, PagesQuestion } from 'apps/page-builder/generated';
import { GroupRequest } from 'apps/page-builder/hooks/api/useGroupSubsection';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';

import styles from './repeating-block.module.scss';

type Props = {
    questions: PagesQuestion[];
    valid: boolean;
    setValid: (valid: boolean) => void;
};

const YES_NO_OPTIONS = [
    { name: 'Yes', value: 'Y' },
    { name: 'No', value: 'N' },
];

export const RepeatingBlock = ({ questions, valid, setValid }: Props) => {
    const [total, setTotal] = useState<number | undefined>(undefined);
    const { control, setValue } = useFormContext<GroupRequest & { batches: Batch[] }>();
    const { fields } = useFieldArray({
        control,
        name: 'batches',
    });

    const batches = useWatch({ control, name: 'batches' });
    const calcTotal = (batches: Batch[]): number => {
        return batches
            .filter((batch) => batch.appearsInTable)
            .reduce((n, { width }) => n + parseInt(String(width ?? 0)), 0);
    };

    useEffect(() => {
        validateWidths();
        batches.forEach((b, i) => {
            if (!b.appearsInTable) {
                setValue(`batches.${i}.label`, '');
                setValue(`batches.${i}.width`, 0);
            }
        });
    }, [JSON.stringify(batches)]);

    const validateWidths = () => {
        const calculated = calcTotal(batches);
        setTotal(isNaN(calculated) ? undefined : calculated);
        setValid(calculated === 100);
    };

    return (
        <div className={styles.block}>
            <div className={styles.header}>
                <h4>Repeating block</h4>
            </div>
            <table className={styles.table}>
                <thead>
                    <tr className={`${styles.row} ${styles.header}`}>
                        <th className={styles.number}>
                            <p>No</p>
                        </th>
                        <th className={styles.name}>
                            <p>Question</p>
                        </th>
                        <th className={styles.appears}>
                            <p>Appears in table</p>
                        </th>
                        <th className={styles.label}>
                            <p>Label in table</p>
                        </th>
                        <th className={styles.width}>
                            <p>Table column %</p>
                        </th>
                    </tr>
                </thead>
                <tbody data-testid="group-questions-tbody">
                    {fields.map((_item: Batch, index: number) => (
                        <tr className={styles.row} key={index}>
                            <td className={styles.number}>
                                <p>{index + 1}</p>
                            </td>
                            <td className={styles.name}>{`${questions[index]?.name} (${questions[index]?.id})`}</td>
                            <td className={styles.appears}>
                                <Controller
                                    control={control}
                                    name={`batches.${index}.appearsInTable`}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <SingleSelect
                                            id={name}
                                            name={name}
                                            label=""
                                            onChange={(v) => onChange(v?.value === 'Y')}
                                            value={YES_NO_OPTIONS.find((o) => o.value === (value ? 'Y' : 'N'))}
                                            options={YES_NO_OPTIONS}
                                            required={true}
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </td>
                            <td className={styles.label}>
                                <Controller
                                    control={control}
                                    name={`batches.${index}.label`}
                                    rules={{
                                        required: { value: batches[index].appearsInTable, message: 'Enter label' },
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <Input
                                            type="text"
                                            name={name}
                                            defaultValue={value}
                                            onChange={onChange}
                                            error={error?.message}
                                            disabled={!batches[index].appearsInTable}
                                        />
                                    )}
                                />
                            </td>
                            <td className={styles.width}>
                                <Controller
                                    control={control}
                                    name={`batches.${index}.width`}
                                    rules={{
                                        required: { value: batches[index].appearsInTable, message: 'Define width' },
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <Input
                                            type="number"
                                            name={name}
                                            data-testid="group-questions-width"
                                            min={0}
                                            max={100}
                                            defaultValue={value?.toString() ?? ''}
                                            onChange={onChange}
                                            error={error?.message}
                                            disabled={!batches[index].appearsInTable}
                                        />
                                    )}
                                />
                            </td>
                        </tr>
                    ))}
                </tbody>
                <tfoot>
                    <tr>
                        <td className={`${!valid ? '' : styles.valid}`}>
                            <p>Columns must total 100%:</p>
                            <div data-testid="columnMustTotal" className={styles.currentPercent}>
                                {total}%
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    );
};
