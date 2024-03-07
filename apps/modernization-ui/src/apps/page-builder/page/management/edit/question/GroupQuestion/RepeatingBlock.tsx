import styles from './repeating-block.module.scss';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
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
    const [total, setTotal] = useState<number>(0);
    const { control, getValues } = useFormContext();

    const watchedBatches = useWatch({ control: control, name: 'batches' });

    const calcTotal = () => {
        const appears = watchedBatches.filter((batch: Batch) => batch.batchTableAppearIndCd === 'Y');
        const total = appears.reduce((n: number, { batchTableColumnWidth }: any) => {
            if (batchTableColumnWidth) {
                return n + parseInt(batchTableColumnWidth);
            } else {
                return n;
            }
        }, 0);
        return total;
    };

    useEffect(() => {
        const calculated = calcTotal();
        setTotal(isNaN(calculated) ? 0 : calculated);
        if (calcTotal() === 100) {
            setValid(true);
        } else {
            setValid(false);
        }
    }, [watchedBatches]);

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
                    {questions.map((batch: Batch, index: number) => (
                        <tr className={styles.row} key={index}>
                            <td className={styles.number}>
                                <p>{index + 1}</p>
                            </td>
                            <td className={styles.name}>{questions[index]?.name}</td>
                            <td className={styles.appears}>
                                <Controller
                                    control={control}
                                    defaultValue={'Y'}
                                    name={`batches[${index}].batchTableAppearIndCd`}
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
                                        required: {
                                            value:
                                                getValues(`batches[${index}].batchTableAppearIndCd`) === 'Y'
                                                    ? true
                                                    : false,
                                            message: 'Enter label'
                                        }
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
                                        required: {
                                            value:
                                                getValues(`batches[${index}].batchTableAppearIndCd`) === 'Y'
                                                    ? true
                                                    : false,
                                            message: 'Define width'
                                        },
                                        pattern: {
                                            value:
                                                getValues(`batches[${index}].batchTableAppearIndCd`) === 'Y'
                                                    ? /^[0-9]*[1-9][0-9]*$/
                                                    : /^(?!)/,
                                            message: 'Width must be a number greater than 0.'
                                        }
                                    }}
                                    render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                        <Input
                                            type="number"
                                            name={name}
                                            defaultValue={batch?.batchTableAppearIndCd === 'N' ? undefined : value}
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
