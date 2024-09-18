import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { NameEntryFields } from 'apps/patient/profile/names/NameEntryFields';
import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { NameEntryView } from './NameEntryView';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';

const defaultValue: NameEntry = {
    asOf: internalizeDate(new Date()),
    type: '',
    prefix: '',
    first: '',
    middle: '',
    secondMiddle: '',
    last: '',
    secondLast: '',
    suffix: '',
    degree: ''
};

const columns: Column<NameEntry>[] = [
    { id: 'nameAsOf', name: 'As of', render: (v) => v.asOf },
    { id: 'nameType', name: 'Type', render: (v) => v.type },
    { id: 'nameLast', name: 'Last', render: (v) => v.last },
    { id: 'nameFirst', name: 'First', render: (v) => v.first },
    { id: 'nameMiddle', name: 'Middle', render: (v) => v.middle },
    { id: 'nameSuffix', name: 'Suffix', render: (v) => v.suffix }
];

type Props = {
    onChange: (data: NameEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};

export const NameMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <NameEntryFields />;
    const renderView = (entry: NameEntry) => <NameEntryView entry={entry} />;

    return (
        <MultiValueEntry
            id="section-Name"
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
