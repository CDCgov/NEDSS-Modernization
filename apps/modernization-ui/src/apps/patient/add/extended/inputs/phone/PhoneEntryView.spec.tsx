import { render } from '@testing-library/react';
import { PhoneEmailEntry } from 'apps/patient/data';
import { PhoneEntryView } from './PhoneEntryView';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const entry: PhoneEmailEntry = {
    asOf: '12/25/2020',
    type: { name: 'Phone', value: 'PH' },
    use: { name: 'Home', value: 'H' },
    countryCode: '1',
    phoneNumber: '123-456-7890',
    extension: '2',
    email: 'email@email.com',
    url: 'someUrl@nbs.gov',
    comment: 'test comments'
};

describe('PhoneEntryView', () => {
    it('should render label with value', () => {
        const { getByText } = render(<PhoneEntryView entry={entry} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const type = getByText('Type');
        expect(type.parentElement).toContainElement(getByText('Phone'));

        const use = getByText('Use');
        expect(use.parentElement).toContainElement(getByText('Home'));

        const countryCode = getByText('Country code');
        expect(countryCode.parentElement).toContainElement(getByText('1'));

        const number = getByText('Phone number');
        expect(number.parentElement).toContainElement(getByText('123-456-7890'));

        const extension = getByText('Extension');
        expect(extension.parentElement).toContainElement(getByText('2'));

        const email = getByText('Email');
        expect(email.parentElement).toContainElement(getByText('email@email.com'));

        const url = getByText('URL');
        expect(url.parentElement).toContainElement(getByText('someUrl@nbs.gov'));

        const comments = getByText('Phone & email comments');
        expect(comments.parentElement).toContainElement(getByText('test comments'));
    });
});
