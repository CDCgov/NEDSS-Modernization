import { asAddressTypeUse } from './utils';

describe('asAddressTypeUse', () => {
    it('should display the type and use as a string', () => {
        const address = {
            type: 'Dormitory',
            use: 'Home'
        };

        expect(asAddressTypeUse(address)).toBe('Dormitory/Home');
    });

    it('should display only the type when use is null', () => {
        const address = {
            type: 'Dormitory',
            use: null
        };

        expect(asAddressTypeUse(address)).toBe('Dormitory');
    });

    it('should display only the use without slash when type is null', () => {
        const address = {
            type: null,
            use: 'Home'
        };

        expect(asAddressTypeUse(address)).toBe('Home');
    });

    it('should display empty string when type and use are null', () => {
        const address = {
            type: null,
            use: null
        };

        expect(asAddressTypeUse(address)).toBe('');
    });
});
