import { DetailValue, DetailView } from 'design-system/entry/multi-value';
import { internalizeDate } from 'date';
import { PhoneEmailDemographic } from './phoneEmails';

type PhoneEmailDemographicViewProps = {
    entry: PhoneEmailDemographic;
};

const PhoneEmailDemographicView = ({ entry }: PhoneEmailDemographicViewProps) => {
    return (
        <DetailView>
            <DetailValue label="Phone & email as of">{internalizeDate(entry.asOf)}</DetailValue>
            <DetailValue label="Type">{entry.type.name}</DetailValue>
            <DetailValue label="Use">{entry.use.name}</DetailValue>
            <DetailValue label="Country code">{entry.countryCode}</DetailValue>
            <DetailValue label="Phone number">{entry.phoneNumber}</DetailValue>
            <DetailValue label="Extension">{entry.extension}</DetailValue>
            <DetailValue label="Email">{entry.email}</DetailValue>
            <DetailValue label="URL">{entry.url}</DetailValue>
            <DetailValue label="Comments">{entry.comment}</DetailValue>
        </DetailView>
    );
};

export { PhoneEmailDemographicView };
export type { PhoneEmailDemographicViewProps };
