import { PhoneEmailEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';

type Props = {
    entry: PhoneEmailEntry;
    sizing?: Sizing;
};
export const PhoneEntryView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} sizing={sizing} required />
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required />
            <ValueView title="Use" value={entry.use?.name} sizing={sizing} required />
            <ValueView title="Country code" value={entry.countryCode} sizing={sizing} />
            <ValueView title="Phone number" value={entry.phoneNumber} sizing={sizing} />
            <ValueView title="Extension" value={entry.extension} sizing={sizing} />
            <ValueView title="Email" value={entry.email} sizing={sizing} />
            <ValueView title="URL" value={entry.url} sizing={sizing} />
            <ValueView title="Phone & email comments" value={entry.comment} sizing={sizing} />
        </>
    );
};
