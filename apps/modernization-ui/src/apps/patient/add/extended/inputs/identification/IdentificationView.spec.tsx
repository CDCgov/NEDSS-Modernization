import { render } from '@testing-library/react';
import { IdentificationEntry } from 'apps/patient/data';
import { IdentificationView } from './IdentificationView';

const entry: IdentificationEntry = {
    asOf: '12/25/2020',
    type: { name: 'Account number', value: 'AN' },
    issuer: { name: 'Texas', value: 'TX' },
    id: '1122'
};

describe('IdentificationView', () => {
    it('should render label with values', () => {
        const { getByText } = render(<IdentificationView entry={entry} />);
        const asOf = getByText('Identification as of');
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const type = getByText('Type');
        expect(type.parentElement).toContainElement(getByText('Account number'));

        const issuer = getByText('Assigning authority');
        expect(issuer.parentElement).toContainElement(getByText('Texas'));

        const value = getByText('ID value');
        expect(value.parentElement).toContainElement(getByText('1122'));
    });

    it('should display required for the proper inputs', () => {
        const { getByText } = render(<IdentificationView entry={entry} />);
        const asOf = getByText('Identification as of');
        expect(asOf).toHaveClass('required');

        const type = getByText('Type');
        expect(type).toHaveClass('required');

        const issuer = getByText('Assigning authority');
        expect(issuer).not.toHaveClass('required');

        const value = getByText('ID value');
        expect(value).toHaveClass('required');
    });
});
