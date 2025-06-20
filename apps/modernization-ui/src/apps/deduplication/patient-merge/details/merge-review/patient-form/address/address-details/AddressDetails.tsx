import { MergeAddress } from 'apps/deduplication/api/model/MergeCandidate';
import { DetailsSection } from '../../shared/details-section/DetailsSection';
import { toDateDisplay } from '../../../../shared/toDateDisplay';

type Props = {
    address: MergeAddress;
};
export const AddressDetails = ({ address }: Props) => {
    return (
        <DetailsSection
            details={[
                { label: 'As of date', value: toDateDisplay(address.asOf) },
                { label: 'Type', value: address.type },
                { label: 'Use', value: address.use },
                { label: 'Street address 1', value: address.address },
                { label: 'Street address 2', value: address.address2 },
                { label: 'City', value: address.city },
                { label: 'State', value: address.state },
                { label: 'Zip', value: address.zipcode },
                { label: 'County', value: address.county },
                { label: 'Census tract', value: address.censusTract },
                { label: 'Country', value: address.country },
                { label: 'Address comments', value: address.comments }
            ]}
        />
    );
};
