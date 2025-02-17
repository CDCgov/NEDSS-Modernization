import { ReactNode } from 'react';
import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { BasicIdentificationEntry } from '../entry';
import { initial } from './entry';
import { BasicIdentificationFields } from './BasicIdentificationFields';
import { BasicIdentificationView } from './BasicIdentificationView';
import styles from './identification.module.scss';

const defaultValue: Partial<BasicIdentificationEntry> = initial();

type BasicIdentificationRepeatingBlockProps = {
    id: string;
    values?: BasicIdentificationEntry[];
    onChange: (data: BasicIdentificationEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};

export const BasicIdentificationRepeatingBlock = ({
    id,
    values,
    onChange,
    isDirty,
    errors
}: BasicIdentificationRepeatingBlockProps) => {
    const renderForm = () => <BasicIdentificationFields />;
    const renderView = (entry: BasicIdentificationEntry) => <BasicIdentificationView entry={entry} />;

    const columns: Column<BasicIdentificationEntry>[] = [
        { id: 'identificationType', name: 'Type', className: styles['col-type'], render: (v) => v.type?.name },
        {
            id: 'assigningAuthority',
            name: 'Assigning authority',
            className: styles['col-authority'],
            render: (v) => v.issuer?.name
        },
        { id: 'idValue', name: 'ID value', className: styles['col-id'], render: (v) => v.id }
    ];
    return (
        <RepeatingBlock<BasicIdentificationEntry>
            id={id}
            title="Identification"
            defaultValues={defaultValue}
            values={values}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};

export type { BasicIdentificationRepeatingBlockProps };
