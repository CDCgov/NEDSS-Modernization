import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { internalizeDate } from 'date';
import { IdentificationDemographic } from './identifications';

type IdentificationDemographicViewProps = {
    entry: IdentificationDemographic;
};

const IdentificationDemographicView = ({ entry }: IdentificationDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label="Identification as of">{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label="Type">{entry.type?.name}</DetailValue>
            <DetailValue label="Assigning authority">{entry.issuer?.name}</DetailValue>
            <DetailValue label="ID value">{entry.value}</DetailValue>
        </DetailView>
    );
};

export { IdentificationDemographicView };
export type { IdentificationDemographicViewProps };
