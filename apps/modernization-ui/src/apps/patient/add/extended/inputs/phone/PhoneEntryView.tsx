import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: PhoneEmailFields;
};
export const PhoneEntryView = ({ entry }: Props) => {
    const coded = usePatientPhoneCodedValues();
    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Type" value={coded.types.find((e) => e.value === entry.type)?.name} required />
            <DataDisplay title="Use" value={coded.uses.find((e) => e.value === entry.use)?.name} required />
            <DataDisplay title="Country code" value={entry.countryCode} />
            <DataDisplay title="Phone number" value={entry.number} />
            <DataDisplay title="Extension" value={entry.extension} />
            <DataDisplay title="Email" value={entry.email} />
            <DataDisplay title="URL" value={entry.url} />
            <DataDisplay title="Phone & email comments" value={entry.comment} />
        </>
    );
};
