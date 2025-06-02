import { PatientPhoneEmail } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { DetailsSection } from '../../shared/details-section/DetailsSection';
import { formatPhone } from '../formatPhone';

type Props = {
    phoneEmail: PatientPhoneEmail;
};
export const PhoneEmailDetails = ({ phoneEmail }: Props) => {
    return (
        <DetailsSection
            details={[
                {
                    label: 'As of date',
                    value: phoneEmail.asOf ? format(parseISO(phoneEmail.asOf), 'MM/dd/yyyy') : '---'
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
