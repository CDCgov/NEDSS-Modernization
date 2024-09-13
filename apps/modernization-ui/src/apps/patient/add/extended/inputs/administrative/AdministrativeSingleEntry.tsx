import { internalizeDate } from 'date';
import { SingleValueEntry } from 'design-system/entry/single-value/SingleValueEntry';
import { AdministrativeEntryFields } from 'apps/patient/profile/administrative/AdministrativeEntryFields';
import { AdministrativeEntry } from 'apps/patient/profile/administrative/AdministrativeEntry';

const defaultValue: AdministrativeEntry = {
    asOf: internalizeDate(new Date()),
    comment: ''
};

type Props = {
    onChange: (data: AdministrativeEntry) => void;
    isDirty: (isDirty: boolean) => void;
};

export const AdministrativeSingleEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <AdministrativeEntryFields />;

    return (
        <SingleValueEntry<AdministrativeEntry>
            id="administrative"
            title="Administrative"
            defaultValues={defaultValue}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
        />
    );
};
