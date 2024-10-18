import { ItemGroup } from 'design-system/item';
import { displayAddress, displayAddressText } from './displayAddress';

describe('when given an address', () => {
    it('should display the formatted address as a string', () => {
        const address = {
            address: '14 North Moore St',
            address2: 'Suite A',
            city: 'New York',
            state: 'NY',
            zipcode: '10013'
        };

        expect(displayAddressText(address)).toBe('14 North Moore St, Suite A\nNew York, NY 10013');
    });

    it('should display the address as an address element', () => {
        const address = {
            use: 'Home',
            address: '14 North Moore St',
            address2: 'Suite A',
            city: 'New York',
            state: 'NY',
            zipcode: '10013'
        };
        const addressElement = displayAddress(address);
        expect(addressElement.type).toBe(ItemGroup);
        expect(addressElement.props.children).toBe('14 North Moore St, Suite A\nNew York, NY 10013');
    });
});
