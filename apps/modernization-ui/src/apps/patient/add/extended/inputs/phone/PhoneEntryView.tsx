import { PhoneEmailEntry } from 'apps/patient/data/entry';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: PhoneEmailEntry;
};
export const PhoneEntryView = ({ entry }: Props) => {
    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Type" value={entry.type.name} required />
            <DataDisplay title="Use" value={entry.use.name} required />
            <DataDisplay title="Country code" value={entry.countryCode} />
            <DataDisplay title="Phone number" value={entry.phoneNumber} />
            <DataDisplay title="Extension" value={entry.extension} />
            <DataDisplay title="Email" value={entry.email} />
            <DataDisplay title="URL" value={entry.url} />
            <DataDisplay title="Phone & email comments" value={entry.comment} />
        </>
    );
};
