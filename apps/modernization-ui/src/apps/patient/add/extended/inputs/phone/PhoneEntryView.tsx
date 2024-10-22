import { PhoneEmailEntry } from 'apps/patient/data/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { asName } from 'options';

type Props = {
    entry: PhoneEmailEntry;
};
export const PhoneEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Type" value={asName(entry.type)} required />
            <ValueView title="Use" value={asName(entry.use)} required />
            <ValueView title="Country code" value={entry.countryCode} />
            <ValueView title="Phone number" value={entry.phoneNumber} />
            <ValueView title="Extension" value={entry.extension} />
            <ValueView title="Email" value={entry.email} />
            <ValueView title="URL" value={entry.url} />
            <ValueView title="Phone & email comments" value={entry.comment} />
        </>
    );
};
