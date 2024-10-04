import { IdentificationEntry } from 'apps/patient/data/entry';
import { IdentificationEntryFields } from 'apps/patient/data/identification/IdentificationEntryFields';
import { today } from 'date';
import { RepeatingBlock } from 'design-system/entry/multi-value/RepeatingBlock';
import { Column } from 'design-system/table';
import { IdentificationView } from './IdentificationView';
import { ReactNode } from 'react';

const defaultValue: Partial<IdentificationEntry> = {
    asOf: today(),
    type: undefined,
    issuer: undefined,
    id: ''
};
type Props = {
    onChange: (data: IdentificationEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};
export const IdentificationRepeatingBlock = ({ onChange, isDirty, errors }: Props) => {
    const renderForm = () => <IdentificationEntryFields />;
    const renderView = (entry: IdentificationEntry) => <IdentificationView entry={entry} />;

    const columns: Column<IdentificationEntry>[] = [
        { id: 'identificationAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'identificationType', name: 'Type', render: (v) => v.type.name },
        { id: 'assigningAuthority', name: 'Authority', render: (v) => v.issuer?.name },
        { id: 'idValue', name: 'Value', render: (v) => v.id }
    ];
    return (
        <RepeatingBlock<IdentificationEntry>
            id="identification"
            title="Identification"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};
