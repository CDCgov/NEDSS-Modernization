import { render, within } from '@testing-library/react';
import { MergePhoneEmail } from 'apps/deduplication/api/model/MergeCandidate';
import { PhoneEmailDetails } from './PhoneEmailDetails';

const defaultPhoneEmail: MergePhoneEmail = {
    id: '123',
    asOf: '2022-06-07T14:24:44.970',
    type: 'Cellular Phone',
    use: 'Home',
    countryCode: '1',
    phoneNumber: '1234445555',
    extension: '12',
    email: 'email@email.com',
    url: 'url@url.com',
    comments: 'phone comment'
};

const Fixture = ({ phoneEmail = defaultPhoneEmail }: { phoneEmail?: MergePhoneEmail }) => {
    return <PhoneEmailDetails phoneEmail={phoneEmail} />;
};

describe('PhoneEmailDetails', () => {
    it('should render the proper labels', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Type')).toBeInTheDocument();
        expect(getByText('Use')).toBeInTheDocument();
        expect(getByText('Country code')).toBeInTheDocument();
        expect(getByText('Phone number')).toBeInTheDocument();
        expect(getByText('Extension')).toBeInTheDocument();
        expect(getByText('Email')).toBeInTheDocument();
        expect(getByText('URL')).toBeInTheDocument();
        expect(getByText('Phone & email comments')).toBeInTheDocument();
    });

    it('should render "---" for missing optional fields', () => {
        const sparsePhoneEmail: MergePhoneEmail = {
            id: '1',
            asOf: '2022-06-07T14:24:44.970',
            type: 'Type',
            use: 'Use'
        };
        const { getByText } = render(<Fixture phoneEmail={sparsePhoneEmail} />);
        expect(within(getByText('Country code').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Phone number').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Extension').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Email').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('URL').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Phone & email comments').parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should display as of date in proper format', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('06/07/2022')).toBeInTheDocument();
    });
});
