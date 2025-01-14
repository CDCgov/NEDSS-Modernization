import { PhoneEmailEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: PhoneEmailEntry;
};
export const PhoneEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Type" value={entry.type?.name} required />
            <ValueView title="Use" value={entry.use?.name} required />
            <ValueView title="Country code" value={entry.countryCode} />
            <ValueView title="Phone number" value={entry.phoneNumber} />
            <ValueView title="Extension" value={entry.extension} />
            <ValueView title="Email" value={entry.email} />
            <ValueView title="URL" value={entry.url} />
            <ValueView title="Phone & email comments" value={entry.comment} />
        </>
    );
};
