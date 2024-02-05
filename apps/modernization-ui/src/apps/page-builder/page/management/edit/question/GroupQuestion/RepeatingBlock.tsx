import styles from './repeating-block.module.scss';
import { Controller, useFieldArray, useFormContext, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { Batch, PagesQuestion } from 'apps/page-builder/generated';
import { useEffect, useState } from 'react';

type Props = {
    questions: PagesQuestion[];
    valid: boolean;
    setValid: (valid: boolean) => void;
};

export const RepeatingBlock = ({ questions, valid, setValid }: Props) => {
    const [total, setTotal] = useState<number | undefined>(undefined);
    const { control } = useFormContext();
    const { fields } = useFieldArray({
        control,
        name: 'batches'
    });

    const watchedWidth = useWatch({ control: control });

    const calcTotal = (batches: Batch[]) => {
        return batches.reduce((n: any, { batchTableColumnWidth }: any) => n + parseInt(batchTableColumnWidth), 0);
    };

    useEffect(() => {
        const calculated = calcTotal(watchedWidth.batches);
        setTotal(isNaN(calculated) ? undefined : calculated);
        if (calcTotal(watchedWidth.batches) === 100) {
            setValid(true);
        } else {
            setValid(false);
        }
    }, [watchedWidth]);

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
                            <p>Questions?</p>
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
                <tbody>
                    {fields.map((item: any, index: number) => (
                        <tr className={styles.row} key={index}>
                            <td className={styles.number}>
                                <p>{index + 1}</p>
                            </td>
                            <td className={styles.name}>{questions[index]?.name}</td>
                            <td className={styles.appears}>
                                <Controller
                                    control={control}
                                    name={`batches[${index}].batchTableAppearIndCd`}
                                    rules={{
                                        required: { value: true, message: "Select 'Yes' or 'No'" }
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <SelectInput
                                            name={name}
                                            onChange={onChange}
                                            defaultValue={value}
                                            options={[
                                                { name: 'Yes', value: 'Y' },
                                                { name: 'No', value: 'N' }
                                            ]}
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </td>
                            <td className={styles.label}>
                                <Controller
                                    control={control}
                                    name={`batches[${index}].batchTableHeader`}
                                    rules={{
                                        required: { value: true, message: 'Enter label' }
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <Input
                                            type="text"
                                            name={name}
                                            defaultValue={value}
                                            onChange={onChange}
                                            error={error?.message}
                                        />
                                    )}
                                />
                            </td>
                            <td className={styles.width}>
                                <Controller
                                    control={control}
                                    name={`batches[${index}].batchTableColumnWidth`}
                                    rules={{
                                        required: { value: true, message: 'Define width' }
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <Input
                                            type="number"
                                            name={name}
                                            defaultValue={value}
                                            onChange={onChange}
                                            error={error?.message}
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
                            <h4>
                                {total}
                                {total ? '%' : null}
                            </h4>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    );
};
