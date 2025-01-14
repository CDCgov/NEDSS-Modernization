import { RaceEntry } from 'apps/patient/data/race';
import { ValueView } from 'design-system/data-display/ValueView';
import { asName } from 'options';
import { maybeMapAll } from 'utils/mapping';

const renderDetails = maybeMapAll(asName);

type Props = {
    entry: RaceEntry;
};
export const RaceEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Race" value={entry.race?.name} required />
            <ValueView title="Detailed race" value={renderDetails(entry.detailed).join(', ')} />
        </>
    );
};
