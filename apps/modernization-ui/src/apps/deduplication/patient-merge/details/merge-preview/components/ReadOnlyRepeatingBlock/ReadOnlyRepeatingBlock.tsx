import { RepeatingBlock } from 'design-system/entry/multi-value/RepeatingBlock';
import { Column } from '../../../../../../../design-system/table';
import { FieldValues } from 'react-hook-form';
import styles from './ReadOnlyRepeatingBlock.module.scss';

export const ReadOnlyRepeatingBlock = <V extends FieldValues>({
    id,
    title,
    columns,
    values
}: {
    id: string;
    title: string;
    columns: Column<V>[];
    values: V[];
}) => {
    return (
        <div className={styles['read-only-repeating-block']}>
            <RepeatingBlock
                id={id}
                title={title}
                columns={columns}
                values={values}
                onChange={() => {}}
                isDirty={() => {}}
                formRenderer={() => null}
                viewRenderer={() => null}
            />
        </div>
    );
};
