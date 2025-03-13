import { RaceEntry } from 'apps/patient/data/race';
import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';
import { asName } from 'options';
import { maybeMapAll } from 'utils/mapping';

const renderDetails = maybeMapAll(asName);

type Props = {
    entry: RaceEntry;
    sizing?: Sizing;
};
export const RaceEntryView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} sizing={sizing} required />
            <ValueView title="Race" value={entry.race?.name} sizing={sizing} required />
            <ValueView title="Detailed race" value={renderDetails(entry.detailed).join(', ')} sizing={sizing} />
        </>
    );
};
