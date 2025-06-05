import { MergePhoneEmail } from 'apps/deduplication/api/model/MergeCandidate';
import { DetailsSection } from '../../shared/details-section/DetailsSection';
import { toDateDisplay } from '../../shared/toDateDisplay';
import { formatPhone } from '../formatPhone';

type Props = {
    phoneEmail: MergePhoneEmail;
};
export const PhoneEmailDetails = ({ phoneEmail }: Props) => {
    return (
        <DetailsSection
            details={[
                {
                    label: 'As of date',
                    value: toDateDisplay(phoneEmail.asOf)
                },
                { label: 'Type', value: phoneEmail.type },
                { label: 'Use', value: phoneEmail.use },
                { label: 'Country code', value: phoneEmail.countryCode },
                { label: 'Phone number', value: formatPhone(phoneEmail.phoneNumber) },
                { label: 'Extension', value: phoneEmail.extension },
                { label: 'Email', value: phoneEmail.email },
                { label: 'URL', value: phoneEmail.url },
                { label: 'Phone & email comments', value: phoneEmail.comments }
            ]}
        />
    );
};
