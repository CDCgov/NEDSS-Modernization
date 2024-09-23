import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { NameEntryView } from './NameEntryView';
import { NameEntry } from 'apps/patient/data/entry';
import { NameEntryFields } from 'apps/patient/data/name/NameEntryFields';

const defaultValue: Partial<NameEntry> = {
    asOf: internalizeDate(new Date()),
    type: undefined,
    prefix: undefined,
    first: '',
    middle: '',
    secondMiddle: '',
    last: '',
    secondLast: '',
    suffix: undefined,
    degree: undefined
};

const columns: Column<NameEntry>[] = [
    { id: 'nameAsOf', name: 'As of', render: (v) => v.asOf },
    { id: 'nameType', name: 'Type', render: (v) => v.type.name },
    { id: 'nameLast', name: 'Last', render: (v) => v.last },
    { id: 'nameFirst', name: 'First', render: (v) => v.first },
    { id: 'nameMiddle', name: 'Middle', render: (v) => v.middle },
    { id: 'nameSuffix', name: 'Suffix', render: (v) => v.suffix?.name }
];

type Props = {
    onChange: (data: NameEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};

export const NameMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <NameEntryFields />;
    const renderView = (entry: NameEntry) => <NameEntryView entry={entry} />;

    return (
        <MultiValueEntry<NameEntry>
            id="name"
            title="Name"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
        />
    );
};
