import { ReactNode } from 'react';
import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { IdentificationEntryFields, IdentificationEntry, initial } from 'apps/patient/data/identification';
import { IdentificationView } from './IdentificationView';

const defaultValue: Partial<IdentificationEntry> = initial();

type Props = {
    id: string;
    values?: IdentificationEntry[];
    onChange: (data: IdentificationEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};
export const IdentificationRepeatingBlock = ({ id, errors, values, onChange, isDirty }: Props) => {
    const renderForm = () => <IdentificationEntryFields />;
    const renderView = (entry: IdentificationEntry) => <IdentificationView entry={entry} />;

    const columns: Column<IdentificationEntry>[] = [
        { id: 'identificationAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'identificationType', name: 'Type', render: (v) => v.type?.name },
        { id: 'assigningAuthority', name: 'Authority', render: (v) => v.issuer?.name },
        { id: 'idValue', name: 'Value', render: (v) => v.id }
    ];
    return (
        <RepeatingBlock<IdentificationEntry>
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
