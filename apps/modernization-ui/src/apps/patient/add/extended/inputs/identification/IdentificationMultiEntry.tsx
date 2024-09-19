import { IdentificationEntry } from 'apps/patient/data/entry';
import { IdentificationView } from './IdentificationView';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { IdentificationEntryFields } from 'apps/patient/data/identification/IdentificationEntryFields';

const defaultValue: Partial<IdentificationEntry> = {
    asOf: internalizeDate(new Date()),
    type: undefined,
    issuer: undefined,
    id: ''
};
type Props = {
    onChange: (data: IdentificationEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const IdentificationMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <IdentificationEntryFields />;
    const renderView = (entry: IdentificationEntry) => <IdentificationView entry={entry} />;

    const columns: Column<IdentificationEntry>[] = [
        { id: 'identificationAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'identificationType', name: 'Type', render: (v) => v.type.name },
        { id: 'assigningAuthority', name: 'Authority', render: (v) => v.issuer?.name },
        { id: 'idValue', name: 'Value', render: (v) => v.id }
    ];
    return (
        <MultiValueEntry<IdentificationEntry>
            id="identification"
            title="Identification"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
        />
    );
};
