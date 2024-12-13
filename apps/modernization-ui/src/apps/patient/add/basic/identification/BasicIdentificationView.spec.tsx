import { render } from '@testing-library/react';
import { BasicIdentificationView } from './BasicIdentificationView';
import { BasicIdentificationEntry } from '../entry';

const entry: BasicIdentificationEntry = {
    type: { name: 'Account number', value: 'AN' },
    issuer: { name: 'Texas', value: 'TX' },
    id: '1122'
};

describe('BasicIdentificationView', () => {
    it('should render label with values', () => {
        const { getByText } = render(<BasicIdentificationView entry={entry} />);

        const type = getByText('Type');
        expect(type.parentElement).toContainElement(getByText('Account number'));

        const issuer = getByText('Assigning authority');
        expect(issuer.parentElement).toContainElement(getByText('Texas'));

        const value = getByText('ID value');
        expect(value.parentElement).toContainElement(getByText('1122'));
    });

    it('should display required for the proper inputs', () => {
        const { getByText } = render(<BasicIdentificationView entry={entry} />);

        const type = getByText('Type');
        expect(type).toHaveClass('required');

        const issuer = getByText('Assigning authority');
        expect(issuer).not.toHaveClass('required');

        const value = getByText('ID value');
        expect(value).toHaveClass('required');
    });
});
