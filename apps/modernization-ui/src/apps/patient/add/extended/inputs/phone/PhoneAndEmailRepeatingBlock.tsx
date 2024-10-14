import { PhoneEmailEntry } from 'apps/patient/data/entry';
import { PhoneEmailEntryFields } from 'apps/patient/data/phoneEmail/PhoneEmailEntryFields';
import { today } from 'date';
import { RepeatingBlock } from 'design-system/entry/multi-value/RepeatingBlock';
import { Column } from 'design-system/table';
import { PhoneEntryView } from './PhoneEntryView';
import { ReactNode } from 'react';

const defaultValue: Partial<PhoneEmailEntry> = {
    asOf: today(),
    type: undefined,
    use: undefined,
    countryCode: '',
    phoneNumber: '',
    extension: '',
    email: '',
    url: '',
    comment: ''
};
type Props = {
    id: string;
    values?: PhoneEmailEntry[];
    onChange: (data: PhoneEmailEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};
export const PhoneAndEmailRepeatingBlock = ({ id, values, errors, onChange, isDirty }: Props) => {
    const renderForm = () => <PhoneEmailEntryFields />;
    const renderView = (entry: PhoneEmailEntry) => <PhoneEntryView entry={entry} />;

    const columns: Column<PhoneEmailEntry>[] = [
        { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'phoneEmailType', name: 'Type', render: (v) => v.type?.name },
        { id: 'phoneNumber', name: 'Phone number', render: (v) => v.phoneNumber },
        { id: 'email', name: 'Email address', render: (v) => v.email },
        { id: 'comments', name: 'Comments', render: (v) => v.comment }
    ];

    return (
        <RepeatingBlock<PhoneEmailEntry>
            id={id}
            title="Phone & email"
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
