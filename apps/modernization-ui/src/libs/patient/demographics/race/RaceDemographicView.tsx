import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { internalizeDate } from 'date';
import { RaceDemographic } from './race';
import { displayNoData } from 'design-system/data';

type RaceDemographicViewProps = {
    entry: RaceDemographic;
};

const RaceDemographicView = ({ entry }: RaceDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label="Race as of">{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label="Race">{entry.race.name}</DetailValue>
            <DetailValue label="Detailed race">
                {entry.detailed.length > 0 ? entry.detailed.map((detail) => detail.name).join(', ') : displayNoData()}
            </DetailValue>
        </DetailView>
    );
};

export { RaceDemographicView };
export type { RaceDemographicViewProps };
