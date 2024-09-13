import { internalizeDate } from 'date';
import { SingleValueEntry } from 'design-system/entry/single-value/SingleValueEntry';
import { AdministrativeEntryFieldsType } from './administrative';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';

const defaultValue: AdministrativeEntryFieldsType = {
    asOf: internalizeDate(new Date()),
    comment: ''
};

type Props = {
    onChange: (data: AdministrativeEntryFieldsType) => void;
    isDirty: (isDirty: boolean) => void;
};

export const AdministrativeEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <AdministrativeEntryFields />;

    return (
        <SingleValueEntry<AdministrativeEntryFieldsType>
            id="administrative"
            title="Administrative"
            defaultValues={defaultValue}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
        />
    );
};
