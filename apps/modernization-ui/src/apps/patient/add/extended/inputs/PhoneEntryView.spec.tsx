import { render } from '@testing-library/react';
import { PhoneEntryView } from './PhoneEntryView';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const entry: PhoneEmailFields = {
    asOf: '12/25/2020',
    type: 'PH',
    use: 'H',
    countryCode: '1',
    number: '123-456-7890',
    extension: '2',
    email: 'email@email.com',
    url: 'someUrl@nbs.gov',
    comment: 'test comments'
};

describe('PhoneEntryView', () => {
    it('should render label with value', () => {
        const { getByText } = render(<PhoneEntryView entry={entry} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement?.children[1]).toHaveTextContent('12/25/2020');

        const type = getByText('Type');
        expect(type.parentElement?.children[1]).toHaveTextContent('Phone');

        const use = getByText('Use');
        expect(use.parentElement?.children[1]).toHaveTextContent('Home');

        const countryCode = getByText('Country code');
        expect(countryCode.parentElement?.children[1]).toHaveTextContent('1');

        const number = getByText('Phone number');
        expect(number.parentElement?.children[1]).toHaveTextContent('123-456-7890');

        const extension = getByText('Extension');
        expect(extension.parentElement?.children[1]).toHaveTextContent('2');

        const email = getByText('Email');
        expect(email.parentElement?.children[1]).toHaveTextContent('email@email.com');

        const url = getByText('URL');
        expect(url.parentElement?.children[1]).toHaveTextContent('someUrl@nbs.gov');

        const comments = getByText('Phone & email comments');
        expect(comments.parentElement?.children[1]).toHaveTextContent('test comments');
    });
});
