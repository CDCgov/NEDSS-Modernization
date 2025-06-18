import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { NameDemographic } from './names';
import { internalizeDate } from 'date';

type NameDemographicViewProps = {
    entry: NameDemographic;
};

const NameDemographicView = ({ entry }: NameDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label="Name as of">{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label="Type">{entry.type?.name}</DetailValue>
            <DetailValue label="Prefix">{entry.prefix?.name}</DetailValue>
            <DetailValue label="Last">{entry.last}</DetailValue>
            <DetailValue label="Second last">{entry.secondLast}</DetailValue>
            <DetailValue label="First">{entry.first}</DetailValue>
            <DetailValue label="Middle">{entry.middle}</DetailValue>
            <DetailValue label="Second middle">{entry.secondMiddle}</DetailValue>
            <DetailValue label="Suffix">{entry.suffix?.name}</DetailValue>
            <DetailValue label="Degree">{entry.degree?.name}</DetailValue>
        </DetailView>
    );
};

export { NameDemographicView };
export type { NameDemographicViewProps };
