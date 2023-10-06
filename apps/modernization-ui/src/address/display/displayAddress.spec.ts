import { displayAddress } from './displayAddress';

describe('when given an address', () => {
    it('should display the formatted address', () => {
        const address = {
            address: '14 North Moore St',
            address2: 'Suite A',
            city: 'New York',
            state: 'NY',
            zipcode: '10013'
        };

        const actual = expect(displayAddress(address)).toBe('14 North Moore St, Suite A\nNew York, NY, 10013');
    });
});
