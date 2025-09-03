import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { internalizeDate } from 'date';
import { mapOr } from 'utils/mapping';
import { Selectable } from 'options';
import { RaceDemographic, labels } from './race';

const displayDetails = mapOr<Selectable[], string | undefined>(
    (details) => details.map((detail) => detail.name).join(', '),
    undefined
);

type RaceDemographicViewProps = {
    entry: Partial<RaceDemographic>;
};

const RaceDemographicView = ({ entry }: RaceDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label={labels.asOf}>{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label={labels.race}>{entry.race?.name}</DetailValue>
            <DetailValue label={labels.detailed}>{displayDetails(entry.detailed)}</DetailValue>
        </DetailView>
    );
};

export { RaceDemographicView };
export type { RaceDemographicViewProps };
