import { ItemGroup } from 'design-system/item';
import { displayAddress, displayAddressText, displayAddressTypeUse } from './displayAddress';

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

    it('should display the type and use as a string', () => {
        const address = {
            type: 'Dormitory',
            use: 'Home'
        };

        expect(displayAddressTypeUse(address)).toBe('Dormitory/Home');
    });

    it('should display only the type when use is null', () => {
        const address = {
            type: 'Dormitory',
            use: null
        };

        expect(displayAddressTypeUse(address)).toBe('Dormitory');
    });

    it('should display empty string when type and use are null', () => {
        const address = {
            type: null,
            use: null
        };

        expect(displayAddressTypeUse(address)).toBe('');
    });
});
