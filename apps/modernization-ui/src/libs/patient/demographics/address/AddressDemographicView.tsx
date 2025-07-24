import { internalizeDate } from 'date';
import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { AddressDemographic } from './address';

type AddressDemographicViewProps = {
    entry: AddressDemographic;
};

const AddressDemographicView = ({ entry }: AddressDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label="Address as of">{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label="Type">{entry.type.name}</DetailValue>
            <DetailValue label="Use">{entry.use.name}</DetailValue>
            <DetailValue label="Street address 1">{entry.address1}</DetailValue>
            <DetailValue label="Street address 2">{entry.address2}</DetailValue>
            <DetailValue label="City">{entry.city}</DetailValue>
            <DetailValue label="State">{entry.state?.name}</DetailValue>
            <DetailValue label="Zip">{entry.zipcode}</DetailValue>
            <DetailValue label="County">{entry.county?.name}</DetailValue>
            <DetailValue label="Census tract">{entry.censusTract}</DetailValue>
            <DetailValue label="Country">{entry.country?.name}</DetailValue>
            <DetailValue label="Comments">{entry.comment}</DetailValue>
        </DetailView>
    );
};

export { AddressDemographicView };
