import { ReactNode } from 'react';
import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { BasicIdentificationEntry } from '../entry';
import { BasicIdentificationFields } from './BasicIdentificationFields';
import { BasicIdentificationView } from './BasicIdentificationView';

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
        { id: 'identificationType', name: 'Type', render: (v) => v.type?.name },
        { id: 'assigningAuthority', name: 'Authority', render: (v) => v.issuer?.name },
        { id: 'idValue', name: 'Value', render: (v) => v.id }
    ];
    return (
        <RepeatingBlock<BasicIdentificationEntry>
            id={id}
            title="Identification"
            defaultValues={undefined}
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
