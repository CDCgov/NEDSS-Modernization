import { today } from 'date';
import { PhoneEmailDemographic } from './phoneEmails';
import { asSelectable } from 'options';
import { render, screen } from '@testing-library/react';
import { PhoneEmailDemographicView } from './PhoneEmailDemographicView';

const entry: PhoneEmailDemographic = {
    asOf: '2000-01-01',
    type: asSelectable('P', 'Primary phone'),
    use: asSelectable('C', 'Cellular'),
    countryCode: '1',
    phoneNumber: '1112223333',
    extension: '123',
    email: 'test@test.com',
    url: 'test.com',
    comment: 'test comments'
};

describe('phone email demographic', () => {
    it('display as of', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Phone & email as of' });

        expect(actual).toHaveTextContent('01/01/2000');
    });

    it('display type', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Type' });

        expect(actual).toHaveTextContent('Primary phone');
    });

    it('display use', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Use' });

        expect(actual).toHaveTextContent('Cellular');
    });

    it('display country code', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Country code' });

        expect(actual).toHaveTextContent('1');
    });

    it('display phone number', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Phone number' });

        expect(actual).toHaveTextContent('1112223333');
    });

    it('display extension', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Extension' });

        expect(actual).toHaveTextContent('123');
    });

    it('display email', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Email' });

        expect(actual).toHaveTextContent('test@test.com');
    });

    it('display url', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'URL' });

        expect(actual).toHaveTextContent('test.com');
    });

    it('display comments', () => {
        render(<PhoneEmailDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Comments' });

        expect(actual).toHaveTextContent('test comments');
    });
});
